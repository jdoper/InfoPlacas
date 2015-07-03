package com.infoplacas.dao;

import java.util.List;

import javax.ejb.Local;

import com.infoplacas.model.Veiculo;

@Local
public interface VeiculoDAO {
	
	public List<Veiculo> getAllVeiculos();
	
	public Veiculo getVeiculo(String placa);
	
	public void salvar(Veiculo veiculo);
	
	public void atualizar(Veiculo veiculo);
	
	public void remover(String placa);
}
