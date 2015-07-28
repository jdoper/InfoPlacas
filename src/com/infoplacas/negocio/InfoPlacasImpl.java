package com.infoplacas.negocio;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.infoplacas.dao.UsuarioDAO;
import com.infoplacas.dao.VeiculoDAO;
import com.infoplacas.model.Usuario;
import com.infoplacas.model.Veiculo;


@Stateless
public class InfoPlacasImpl implements InfoPlacas {
	// Variavel de controle
	private String mensagem;
	private String placa;
	
	// DAO
	@EJB
	private UsuarioDAO usuarioDAO;
	
	@EJB
	private VeiculoDAO veiculoDAO;
	
	
	/*
	 * Usuario
	 * */
	
	// Cria um novo registro de Usuario
	@Override
	public boolean criarUsuario(Usuario usuario) {
		try {
			usuarioDAO.salvar(usuario);
			return true;
		}
		catch(Exception exception) {
			mensagem = exception.getMessage();
			return false;
		}
	}

	// Busca um usuario, verificando seus parametros
	@Override
	public Usuario buscarUsuario(Usuario usuario) {
		if (usuario.getEmail() != null) {
			mensagem = "Usuario nao encontrado";
			return usuarioDAO.buscar(usuario);
		}
		else {
			mensagem = "Login nao informado";
			return null;
		}
	}
	
	// Busca o registro de Usuario com ID (email) correspondente
	@Override
	public Usuario buscarUsuario(String email) {
		mensagem = "Usuario nao encontrado";
		return usuarioDAO.buscar(email);
	}

	// Remove o registro de Usuario passado
	@Override
	public boolean excluirUsuario(Usuario usuario) {
		try {
			usuarioDAO.remover(usuario);
			return true;
		}
		catch(Exception exception) {
			mensagem = exception.getMessage();
			return false;
		}
	}
	
	// Retorna os veiculos cadastrados de um Usuario
	@Override
	public List<Veiculo> listarVeiculos(Usuario usuario) {
		return veiculoDAO.getUserVeiculos(usuario.getEmail());
	}
	
	
	
	/*
	 * Veiculo
	 * */
	
	// Retorna os veiculos cadastrados
	@Override
	public List<Veiculo> listarVeiculos() {
		return veiculoDAO.getAllVeiculos();
	}

	// Busca veiculo pela placa
	@Override
	public Veiculo buscarVeiculo(String placa) {
		if (verificarPlaca(placa)) {
			mensagem = "Nao existe Veiculo com a placa passada";
			return veiculoDAO.getVeiculo(this.placa);
		}
		else {
			mensagem = "Placa com formato invalido";
			return null;
		}
	}
	
	// Cria um novo registro de Veiculo
	@Override
	public boolean criarVeiculo(Veiculo veiculo) {
		try {
			if (veiculo.getUsuario() != null &&
				buscarUsuario(veiculo.getUsuario()) != null) {
				veiculoDAO.salvar(veiculo);
				return true;
			}
			else if (veiculo.getUsuario() == null) {
				mensagem = "Usuario nao informado";
				return false;
			}
			else {
				mensagem = "Nao foi possivel cadastrar o Veiculo";
				return false;
			}
		}
		catch (Exception exception) {
			mensagem = exception.getMessage();
			return false;
		}
	}

	// Atualiza um registro de Veiculo
	@Override
	public boolean editarVeiculo(Veiculo veiculo) {
		try {
			veiculoDAO.atualizar(veiculo);
			return true;
		}
		catch (Exception exception) {
			mensagem = exception.getMessage();
			return false;
		}
	}

	// Remove um registro de Veiculo
	@Override
	public boolean excluirVeiculo(String placa) {
		try {
			if (verificarPlaca(placa)) {
				veiculoDAO.remover(this.placa);
				return true;
			}
			else {
				mensagem = "Placa com formato invalido";
				return false;
			}
		}
		catch (Exception exception) {
			mensagem = exception.getMessage();
			return false;
		}
	}
	
	
	/*
	 * Controle
	 * */
	
	@Override
	public String getMensagem() {
		return mensagem;
	}

	@Override
	public boolean verificarPlaca(String placa) {
		/*
		 * Formato: ABC-1234
		 * */
		Pattern padrao = Pattern.compile("[A-Z]{3,3}-\\d{4,4}");
        Matcher matcher = padrao.matcher(placa);
        
        if (matcher.find()){
        	this.placa = placa;
        	return true;
        }
        
        /*
		 * Formato: ABC1234
		 * */
		padrao = Pattern.compile("[A-Z]{3,3}\\d{4,4}");
        matcher = padrao.matcher(placa);
        
        if (matcher.find()){
        	String letras = placa.substring(0,3).trim();
    		String numeros = placa.substring(3,7).trim();
    		this.placa = letras + "-" + numeros;
            return true;
        }
        
        /*
		 * Formato: abc-1234
		 * */
		padrao = Pattern.compile("[a-z]{3,3}-\\d{4,4}");
        matcher = padrao.matcher(placa);
        
        if (matcher.find()){
        	String[] partes = placa.split("-");
        	this.placa = partes[0].toUpperCase() + partes[1];
            return true;
        }
        
        /*
		 * Formato: abc1234
		 * */
		padrao = Pattern.compile("[a-z]{3,3}\\d{4,4}");
        matcher = padrao.matcher(placa);
		
        if (matcher.find()){
        	String letras = placa.substring(0,3).trim();
    		String numeros = placa.substring(3,7).trim();
    		this.placa = letras.toUpperCase() + "-" + numeros;
        	return true;
        }
        
        // Nenhum padrao valido
        return false;
	}
}
