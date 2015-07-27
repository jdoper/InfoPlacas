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
		return usuarioDAO.buscar(usuario);
	}
	
	// Busca o registro de Usuario com ID (email) correspondente
	@Override
	public Usuario buscarUsuario(String email) {
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
		return veiculoDAO.getVeiculo(placa);
	}
	
	// Cria um novo registro de Veiculo
	@Override
	public boolean criarVeiculo(Veiculo veiculo) {
		try {
			if (buscarUsuario(veiculo.getUsuario()) != null) {
				veiculoDAO.salvar(veiculo);
				return true;
			}
			
			// Caso nao exista veiculo com o email passado
			mensagem = "O usuario nao existe";
			return false;
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
			veiculoDAO.remover(placa);
			return true;
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
            return true;
        }
        
        /*
		 * Formato: ABC1234
		 * */
		padrao = Pattern.compile("[A-Z]{3,3}\\d{4,4}");
        matcher = padrao.matcher(placa);
        
        if (matcher.find()){
            return true;
        }
        
        /*
		 * Formato: abc-1234
		 * */
		padrao = Pattern.compile("[a-z]{3,3}-\\d{4,4}");
        matcher = padrao.matcher(placa);
        
        if (matcher.find()){
            return true;
        }
        
        /*
		 * Formato: abc1234
		 * */
		padrao = Pattern.compile("[a-z]{3,3}\\d{4,4}");
        matcher = padrao.matcher(placa);
		
        if (matcher.find()){
            return true;
        }
        
        // Nenhum padrao valido
        return false;
	}
}
