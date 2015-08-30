package com.infoplacas.negocio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

import org.json.JSONException;
import org.json.JSONObject;

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
	
	// Variaveis para requisição
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "http://exemplo-teste.herokuapp.com/api/v1/veiculo/";
	
	
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
	@Schedule(second="*", minute="*/1", hour="*")
	@Override
	public void atualizarInformacoes() {
		// Recupera e intera sobre os veiculos cadastrados
		List<Veiculo> veiculos = listarVeiculos();
		for (Veiculo veiculo : veiculos) {
			try {
				// Prepara a requisição
				URL obj = new URL(GET_URL + veiculo.getPlaca());
		        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		        con.setRequestMethod("GET");
		        con.setRequestProperty("User-Agent", USER_AGENT);
		        int responseCode = con.getResponseCode();
		        System.out.println("GET Response Code :: " + responseCode);
		        
		        // Verifica status da resposta
		        if (responseCode == HttpURLConnection.HTTP_OK) { // Sucesso
		            BufferedReader in = new BufferedReader(new InputStreamReader(
		                    con.getInputStream()));
		            String inputLine;
		            StringBuffer response = new StringBuffer();
		            
		            // Recupera informações do response
		            while ((inputLine = in.readLine()) != null) {
		                response.append(inputLine);
		            }
		            in.close();
		 
		            // Formata informações recuperadas
		            JSONObject jsonObject = new JSONObject(response.toString());
		            System.out.println((String) jsonObject.get("placa") + " - " + (String) jsonObject.get("marcaModelo"));
		            
		            // Atualiza informações do veiculo
		            veiculo.setMultas(Float.parseFloat((String) jsonObject.get("multas")));
		            veiculo.setIpva(Float.parseFloat((String) jsonObject.get("ipva")));
		            veiculo.setTaxasDetran(Float.parseFloat((String) jsonObject.get("taxasDetran")));
		            veiculo.setSeguroDPVAT(Float.parseFloat((String) jsonObject.get("seguroDPVAT")));
		            boolean resultado = editarVeiculo(veiculo);
		            
		            System.out.println("Resultado: " + resultado);
		        } 
		        else { // ERRO
		            System.out.println("Não foi possível realizar a requisição");
		        }
			}
			catch (MalformedURLException e) {
				System.out.println(e.getMessage());
			}
			catch (JSONException e) {
				System.out.println(e.getMessage());
			}
			catch (IOException e) {
				System.out.println(e.getMessage());
			} 
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
