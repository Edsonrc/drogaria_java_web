package com.edsonrczarneski.drogaria.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.edsonrczarneski.drogaria.domain.Cliente;

import com.edsonrczarneski.drogaria.util.HibernateUtil;

public class ClienteDAO extends GenericDAO<Cliente> {
	@SuppressWarnings("unchecked")
	public List<Cliente> listarOrdenado() {
		Session sessao = HibernateUtil.getFabricaSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Cliente.class);
			// Criando Alias
			consulta.createAlias("cliente", "c");
			consulta.addOrder(Order.asc("c.nome"));
			List<Cliente> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

}
