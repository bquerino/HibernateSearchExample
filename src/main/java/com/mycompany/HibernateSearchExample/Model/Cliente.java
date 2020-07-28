package com.mycompany.HibernateSearchExample.Model;

import org.hibernate.search.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Indexed
@Table(name = "cliente")
public class Cliente {

	@Id
	private int id;

	@Field(termVector = TermVector.YES)
	private String nomeCliente;

	@Field(termVector = TermVector.YES)
	private String endereco;
	
	@Field
    private int numero;
	
	
	public Cliente(int id, String nomeCliente, String endereco, int numero) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.endereco = endereco;
        this.numero = numero;
    }

    public Cliente() {
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Cliente))
            return false;

        Cliente cliente = (Cliente) o;

        if (id != cliente.id)
            return false;
        if (numero != cliente.numero)
            return false;
        if (!nomeCliente.equals(cliente.nomeCliente))
            return false;
        return endereco.equals(cliente.endereco);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + nomeCliente.hashCode();
        result = 31 * result + endereco.hashCode();
        result = 31 * result + numero;
        
        return result;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
    

}
