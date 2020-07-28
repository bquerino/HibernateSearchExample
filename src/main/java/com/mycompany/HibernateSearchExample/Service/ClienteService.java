package com.mycompany.HibernateSearchExample.Service;

import java.util.List;


import com.mycompany.HibernateSearchExample.Model.Cliente;

public interface ClienteService {
	
	public void adicionaCliente(Cliente novoCliente);
	public List<Cliente> buscaCliente(String text);
	
}
