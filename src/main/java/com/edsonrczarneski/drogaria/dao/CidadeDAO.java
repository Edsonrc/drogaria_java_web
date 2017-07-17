package com.edsonrczarneski.drogaria.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.edsonrczarneski.drogaria.domain.Cidade;
import com.edsonrczarneski.drogaria.util.HibernateUtil;

public class CidadeDAO extends GenericDAO<Cidade> {

	public List<Cidade> buscarPorEstado(Long estadoCodigo){
		Session sessao = HibernateUtil.getFabricaSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Cidade.class);
			consulta.add(Restrictions.eq("estado.codigo", estadoCodigo));
			consulta.addOrder(Order.asc("nome"));
			@SuppressWarnings("unchecked")
			List<Cidade> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}
