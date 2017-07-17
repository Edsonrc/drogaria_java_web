package com.edsonrczarneski.drogaria.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import com.edsonrczarneski.drogaria.dao.ProdutoDAO;
import com.edsonrczarneski.drogaria.domain.Produto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProdutoBean2 implements Serializable {
	private Produto produto;
	private Boolean exibirPainelDados;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Boolean getExibirPainelDados() {
		return exibirPainelDados;
	}

	public void setExibirPainelDados(Boolean exibirPainelDados) {
		this.exibirPainelDados = exibirPainelDados;
	}

	@PostConstruct
	public void novo(){
		produto = new Produto();
		exibirPainelDados = false;
	}
	
	public void buscar(){
		try{
			ProdutoDAO produtoDAO = new ProdutoDAO();
			Produto resultado = produtoDAO.buscar(produto.getCodigo());
			
			if(resultado == null){
				exibirPainelDados = false;
				Messages.addGlobalWarn("Produto não cadastrado!");
			}else{
				exibirPainelDados = true;
				produto = resultado;
			}
			
		}catch(RuntimeException erro){
			Messages.addGlobalInfo("Erro ao buscar produto!");
			erro.printStackTrace();
		}
	}

	

}
