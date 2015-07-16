package com.infoplacas.dao;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.infoplacas.model.Veiculo;

@Stateless
public class VeiculoDAOImpl implements VeiculoDAO {

	@PersistenceContext(unitName="info-placas")
	private EntityManager em;
	
	/*
	 * Retorna lista de todos os veiculos
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<Veiculo> getAllVeiculos() {
		Query consulta = em.createNamedQuery("listaVeiculos");
		return consulta.getResultList();
	}

	/*
	 * Retorna veiculo atraves da placa passada como parametro
	 * */
	@Override
	public Veiculo getVeiculo(String placa) {
		return em.find(Veiculo.class, placa);
	}

	/*
	 * Cria um novo registro de Veiculo
	 * */
	@Override
	public void salvar(Veiculo veiculo) throws Exception {
		try {
			em.persist(veiculo);
		}
		catch (SQLException exception) {
			throw new Exception(exception.getMessage());
		}
	}

	/*
	 * Atualiza registro de Veiculo
	 * */
	@Override
	public void atualizar(Veiculo veiculo) {
		em.merge(veiculo);
	}
	
	/*
	 * Remove veiculo atraves da placa passada
	 * */
	@Override
	public boolean remover(String placa) {
		Veiculo veiculo = em.find(Veiculo.class, placa);
		em.remove(veiculo);
		return true;
	}
}
