package com.mycompany.HibernateSearchExample.Service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.HibernateSearchExample.Dao.BuscaClienteDao;
import com.mycompany.HibernateSearchExample.Model.Cliente;


@Service
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	private BuscaClienteDao buscaClienteDAO;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	/**
	 * Adiciona cliente já indexando ao Lucene(Hibernate search).
	 */
	@Override
	@Transactional
	public void adicionaCliente(Cliente novoCliente) {		
		
		try {
			entityManager.persist(novoCliente);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Ocorreu o seguinte erro: " + e);
		}		
	}
	
	/**
	 * Busca indexada no lucene.
	 */
	@Override
	@Transactional
	public List<Cliente> buscaCliente(String text) {
		
		try {
			List<Cliente> resultados = buscaClienteDAO.buscaNomeDoClientePorPalavraChave(text);
			return resultados;
			
		} catch (Exception e) {
			System.out.println("Ocorreu o seguinte erro: " + e);
		}
		return null;
	}
}
