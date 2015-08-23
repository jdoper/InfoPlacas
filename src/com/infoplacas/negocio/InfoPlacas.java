package com.infoplacas.negocio;

import java.util.List;

import javax.ejb.Local;

import com.infoplacas.model.Usuario;
import com.infoplacas.model.Veiculo;


@Local
public interface InfoPlacas {
	/*
	 * Usuario
	 * */
	public boolean criarUsuario(Usuario usuario);
	public Usuario buscarUsuario(Usuario usuario);
	public Usuario buscarUsuario(String email);
	public boolean excluirUsuario(Usuario usuario);
	public List<Veiculo> listarVeiculos(Usuario usuario);
	
	/*
	 * Veiculo
	 * */
	public List<Veiculo> listarVeiculos();
	public Veiculo buscarVeiculo(String placa);
	public boolean criarVeiculo(Veiculo veiculo);
	public boolean editarVeiculo(Veiculo veiculo);
	public boolean excluirVeiculo(String placa);
	
	/*
	 * Controle
	 * */
	public String getMensagem();
	public boolean verificarPlaca(String placa);
	public void atualizarInformacoes();
}
