package com.edsonrczarneski.drogaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import com.edsonrczarneski.drogaria.dao.CidadeDAO;
import com.edsonrczarneski.drogaria.dao.EstadoDAO;
import com.edsonrczarneski.drogaria.dao.PessoaDAO;
import com.edsonrczarneski.drogaria.domain.Cidade;
import com.edsonrczarneski.drogaria.domain.Estado;
import com.edsonrczarneski.drogaria.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PessoaBean implements Serializable {

	private Pessoa pessoa;
	private List<Pessoa> pessoas;
	private List<Estado> estados;
	private List<Cidade> cidades;
	private Estado estado;// Para carregar na página pessoa lista de cidades

	// Gets e Sets
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@PostConstruct
	public void listar() {
		try {
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as pessoas");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			pessoa = new Pessoa();
			estado = new Estado();
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("nome");

			// Criando uma lista de cidades vazia
			cidades = new ArrayList<Cidade>();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as pessoas");
			erro.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {
		try {
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionada");
			
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar();
			estado = pessoa.getCidade().getEstado();
			
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.buscarPorEstado(estado.getCodigo());
			

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar uma pessoa!");
			erro.printStackTrace();
		}	
	}

	public void salvar() {
		try {
			PessoaDAO pessoaDAO = new PessoaDAO();

			pessoaDAO.merge(pessoa);

			pessoas = pessoaDAO.listar("nome");

			pessoa = new Pessoa();
			estado = new Estado();
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("nome");

			cidades = new ArrayList<Cidade>();
			Messages.addGlobalInfo("Pessoa salvo com sucesso!");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar pessoas.");
			erro.printStackTrace();
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionada");

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.excluir(pessoa);
			
			pessoas = pessoaDAO.listar();
			Messages.addGlobalInfo("Pessoa excluida com sucesso!");
			
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar selecionar uma pessoa!");
			erro.printStackTrace();
		}
	}

	public void popular() {
		try {
			if (estado != null) {
				CidadeDAO cidadeDAO = new CidadeDAO();
				cidades = cidadeDAO.buscarPorEstado(estado.getCodigo());
			} else {
				// Se escolher primeira opção que é nula, cria combo vazio
				cidades = new ArrayList<Cidade>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as cidades");
			erro.printStackTrace();
		}
	}
}
