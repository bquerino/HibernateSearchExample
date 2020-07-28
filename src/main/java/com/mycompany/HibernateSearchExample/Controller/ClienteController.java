package com.mycompany.HibernateSearchExample.Controller;


import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.mycompany.HibernateSearchExample.Model.Cliente;
import com.mycompany.HibernateSearchExample.Service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
		
	@Autowired
	ClienteService clienteService;
		
	@PostMapping
	public ResponseEntity<Cliente> add(@RequestBody Cliente cliente) {
		
		try {
			
			clienteService.adicionaCliente(cliente);
			
		} catch (Exception e) {
			System.out.println("Ocorreu o seguinte erro: " + e);
		}
		
		return ResponseEntity.ok(cliente);

	}
	
	@GetMapping
	public ResponseEntity<List<Cliente>> find(@RequestParam String autocomplete){
		
		List<Cliente> resultados = clienteService.buscaCliente(autocomplete);
		
		if (resultados == null) {
			return (ResponseEntity<List<Cliente>>) ResponseEntity.notFound();
		}
		return ResponseEntity.ok(resultados);
	}

}
