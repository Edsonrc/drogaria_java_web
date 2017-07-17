package com.edsonrczarneski.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import com.edsonrczarneski.drogaria.dao.EstadoDAO;
import com.edsonrczarneski.drogaria.domain.Estado;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EstadoBean implements Serializable {
	private Estado estado;
	private List<Estado> listaEstados;

	// Criando métodos Get e Set
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getListaEstados() {
		return listaEstados;
	}

	public void setListaEstados(List<Estado> listaEstados) {
		this.listaEstados = listaEstados;
	}

	// Para limpar formulário
	public void novo() {
		estado = new Estado();
	}

	// Chamando os métodos
	@PostConstruct
	public void listar() {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			listaEstados = estadoDAO.listar();
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar os Estados!");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.merge(estado);
			
			estado = new Estado();
			listaEstados = estadoDAO.listar();
			Messages.addGlobalInfo("Estado Salvo com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao salvar Estado!");
			erro.printStackTrace();
		}

	}
	
	public void excluir(ActionEvent evento){
		try{
		estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
		
		EstadoDAO estadoDAO = new EstadoDAO();
		estadoDAO.excluir(estado);
		
		listaEstados = estadoDAO.listar();
		
		Messages.addGlobalInfo("Estado removido com sucesso!");
		}catch(RuntimeException erro){
			Messages.addGlobalError("Erro ao excluir Estado!");
			erro.printStackTrace();	    
		}
	}
	
	public void editar(ActionEvent evento){
		estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
	}

}
