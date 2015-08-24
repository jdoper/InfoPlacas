package com.infoplacas.negocio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import sun.net.www.http.HttpClient;

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

	// Diariamente atualiza as informações dos veiculos cadastrados
	@Schedule(second="0", minute="*/1", hour="*")
	@Override
	public void atualizarInformacoes() {
		try {
			// Recupera veiculos e intera sobre eles
			List<Veiculo> veiculos = listarVeiculos();
			for (Veiculo veiculo : veiculos) {
				// Prepara a requisição
				URL url = new URL("https://www.sinesp.gov.br/sinesp-cidadao/api/busca-veiculo");
		        Map<String,Object> params = new LinkedHashMap<>();
		        params.put("placa", veiculo.getPlaca());
		        
		        StringBuilder postData = new StringBuilder();
		        for (Map.Entry<String,Object> param : params.entrySet()) {
		            if (postData.length() != 0) postData.append('&');
		            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
		            postData.append('=');
		            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		        }
		        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

		        // Realiza a requisição
		        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		        conn.setRequestMethod("POST");
		        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
		        conn.setDoOutput(true);
		        conn.getOutputStream().write(postDataBytes);

		        // Atualiza as informações
		        String resultado = "";
		        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		        for ( int c = in.read(); c != -1; c = in.read() ) {
		            resultado += c;
		        }
		        veiculo.setLicenciadoAte(resultado);
			}
		}
		catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		} 
		catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		} 
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
