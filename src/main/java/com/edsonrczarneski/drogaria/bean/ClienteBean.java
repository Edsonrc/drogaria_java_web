package com.edsonrczarneski.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import com.edsonrczarneski.drogaria.dao.ClienteDAO;
import com.edsonrczarneski.drogaria.dao.PessoaDAO;
import com.edsonrczarneski.drogaria.domain.Cliente;
import com.edsonrczarneski.drogaria.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable {
	private Cliente cliente;
	private List<Cliente> listaClientes;
	private List<Pessoa>  listaPessoas;
	
//Métodos Get e Set
	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}
	

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Pessoa> getListaPessoas() {
		return listaPessoas;
	}

	public void setListaPessoas(List<Pessoa> listaPessoas) {
		this.listaPessoas = listaPessoas;
	}

	@PostConstruct
	public void listar() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			listaClientes = clienteDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalInfo("Erro ao listar cidades!");
			erro.printStackTrace();
		}

	}
    
	public void novo(){
		try{
		cliente = new Cliente();
		
		PessoaDAO pessoaDAO = new PessoaDAO();
		listaPessoas = pessoaDAO.listar();
		
		}catch(RuntimeException erro){
			Messages.addGlobalInfo("Erro ao carregar pessoas!");
			erro.printStackTrace();
		}

	}
}
