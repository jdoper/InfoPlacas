package com.infoplacas.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
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
		catch (PersistenceException exception) {
			throw new Exception("Placa existente");
		}
	}

	/*
	 * Atualiza registro de Veiculo
	 * */
	@Override
	public void atualizar(Veiculo veiculo) throws Exception {
		Veiculo existente = em.find(Veiculo.class, veiculo.getPlaca());
		if (existente != null) {
			em.merge(veiculo);
		}
		else {
			throw new Exception("Não exsite veiculo com a placa cadastrada");
		}
	}
	
	/*
	 * Remove veiculo atraves da placa passada
	 * */
	@Override
	public void remover(String placa) throws Exception {
		try {
			Veiculo veiculo = em.find(Veiculo.class, placa);
			em.remove(veiculo);
		}
		catch (IllegalArgumentException exception) {
			throw new Exception("Não exsite veiculo com a placa cadastrada");
		}
	}
}
