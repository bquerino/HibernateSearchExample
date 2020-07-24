package com.mycompany.HibernateSearchExample;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.HibernateSearchExample.Config.HibernateSearchConfig;
import com.mycompany.HibernateSearchExample.Dao.BuscaClienteDao;
import com.mycompany.HibernateSearchExample.Model.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HibernateSearchConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HibernateSearchTest {
	
	@Autowired
	BuscaClienteDao dao;
	
	@PersistenceContext
    private EntityManager entityManager;

    private List<Cliente> clientes;
    
    @Before
    public void loadData() {

        clientes = Arrays.asList(new Cliente(1, "Jose Alves da Silva Sauro", "Rua Muito longe, teste dificilmente voce encontraria", 22),
        						 new Cliente(2, "Janderson Rodolfo da Galera Silva", "Rua Longa teste", 66), 
        						 new Cliente(3, "Kleberson da Rapaziada Silva", "Rua Torta teste", 32),
        						 new Cliente(4, "Bruno Surfista", "Rua do fundo", 1231), 
        						 new Cliente(5, "Maria das Silva Graças Clemente", "Rua Benedito Rui TESTE Barbosa", 900),
        						 new Cliente(6, "Kimberly Clark Johnson", "33th Avenue, NYC - USA", 25), 
        						 new Cliente(7, "Mohammed Johnson Kawasaki", "Travessa Petit Gateau", 62));
    }
    
    @Commit
    @Test
    public void test1_whenInsertData_thenSuccess() {

        for (int i = 0; i < clientes.size() - 1; i++) {
            entityManager.persist(clientes.get(i));
        }
    }
    
    @Test
    public void test2_whenLuceneIndexInitialized_thenCorrectIndexSize() throws InterruptedException {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.createIndexer()
            .startAndWait();
        int indexSize = fullTextEntityManager.getSearchFactory()
            .getStatistics()
            .getNumberOfIndexedEntities(Cliente.class.getName());

        assertEquals(clientes.size() - 1, indexSize);
    }
    
    @Commit
    @Test
    public void test3_whenAdditionalTestDataInserted_thenSuccess() {

        entityManager.persist(clientes.get(clientes.size() - 1));
    }
    
    @Test
    public void test4_whenAdditionalTestDataInserted_thenIndexUpdatedAutomatically() {

        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        int indexSize = fullTextEntityManager.getSearchFactory()
            .getStatistics()
            .getNumberOfIndexedEntities(Cliente.class.getName());

        assertEquals(clientes.size(), indexSize);
    }
    
    @Test
    public void test5_whenKeywordSearchOnName_thenCorrectMatches() {
        List<Cliente> expected = Arrays.asList(clientes.get(0), clientes.get(1), clientes.get(2), clientes.get(4));
        List<Cliente> results = dao.buscaNomeDoClientePorPalavraChave("silva");
        
        assertThat(results, containsInAnyOrder(expected.toArray()));
    }
    
    @Test
    public void test6_whenFuzzySearch_thenCorrectMatches() {
        List<Cliente> expected = Arrays.asList(clientes.get(0), clientes.get(1), clientes.get(2), clientes.get(4));
        List<Cliente> results = dao.buscaPorAproximacao("teste");

        assertThat(results, containsInAnyOrder(expected.toArray()));
    }
    

}
