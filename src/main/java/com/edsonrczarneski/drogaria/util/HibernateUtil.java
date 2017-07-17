package com.edsonrczarneski.drogaria.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	private static SessionFactory fabricaSessoes = criarFabricaSessoes();

	public static SessionFactory getFabricaSessoes() {
		return fabricaSessoes;
	}

	public static Connection getConexao(){
		Session sessao = fabricaSessoes.openSession();
		
		Connection conexao = sessao.doReturningWork(new ReturningWork<Connection>() {
			@Override
			public Connection execute(Connection conn) throws SQLException {
				return conn;
			}
		});
		
		return conexao;
	}

	private static SessionFactory criarFabricaSessoes() {
		try {
			Configuration configuracao = new Configuration().configure();

			ServiceRegistry registro = new StandardServiceRegistryBuilder().applySettings(configuracao.getProperties())
					.build();
			SessionFactory fabrica = configuracao.buildSessionFactory(registro);

			return fabrica;
		} catch (Throwable ex) {
			System.err.println("Fábrica de Sessões não pode ser criada." + ex);
			throw new ExceptionInInitializerError(ex);
		}

	}

}
