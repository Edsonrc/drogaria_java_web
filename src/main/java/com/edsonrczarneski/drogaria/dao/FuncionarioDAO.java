package com.edsonrczarneski.drogaria.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.edsonrczarneski.drogaria.domain.Funcionario;
import com.edsonrczarneski.drogaria.util.HibernateUtil;

public class FuncionarioDAO extends GenericDAO<Funcionario> {
	@SuppressWarnings("unchecked")
	public List<Funcionario> listarOrdenado() {
		Session sessao = HibernateUtil.getFabricaSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Funcionario.class);
			// Criando Alias
			consulta.createAlias("pessoa", "p");
			consulta.addOrder(Order.asc("p.nome"));
			List<Funcionario> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

}
