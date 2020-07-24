package com.mycompany.HibernateSearchExample.Dao;



import org.apache.lucene.search.Query;
import org.hibernate.search.engine.ProjectionConstants;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import com.mycompany.HibernateSearchExample.Model.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BuscaClienteDao {
	
	@PersistenceContext
    private EntityManager entityManager;

    public List<Cliente> buscaNomeDoClientePorPalavraChave(String text) {

        Query palavraChave = getQueryBuilder()
            .keyword()
            .onField("nomeCliente")
            .matching(text)
            .createQuery();

        List<Cliente> resultados = getJpaQuery(palavraChave).getResultList();

        return resultados;
    }
    
    
    public List<Cliente> buscaPorAproximacao(String text) {

        Query fuzzyQuery = getQueryBuilder()
            .keyword()
            .fuzzy()
            .withEditDistanceUpTo(2)
            .withPrefixLength(0)
            .onFields("nomeCliente", "endereco")
            .matching(text)
            .createQuery();

        List<Cliente> results = getJpaQuery(fuzzyQuery).getResultList();

        return results;
    }
    
    
    private FullTextQuery getJpaQuery(org.apache.lucene.search.Query luceneQuery) {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager.createFullTextQuery(luceneQuery, Cliente.class);
    }

    private QueryBuilder getQueryBuilder() {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        return fullTextEntityManager.getSearchFactory()
            .buildQueryBuilder()
            .forEntity(Cliente.class)
            .get();
    }

}
