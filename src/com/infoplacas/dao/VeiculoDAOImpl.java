package com.infoplacas.dao;

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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Veiculo> getAllVeiculos() {
		Query consulta = em.createNamedQuery("listaVeiculos");
		return consulta.getResultList();
	}

	@Override
	public Veiculo getVeiculo(String placa) {
		return em.find(Veiculo.class, placa);
	}

	@Override
	public void salvar(Veiculo veiculo) {
		em.persist(veiculo);
	}

	@Override
	public void atualizar(Veiculo veiculo) {
		em.merge(veiculo);
	}
	
	@Override
	public void remover(String placa) {
		Veiculo veiculo = em.find(Veiculo.class, placa);
		em.remove(veiculo);
	}
}
