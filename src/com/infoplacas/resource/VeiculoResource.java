package com.infoplacas.resource;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.infoplacas.dao.VeiculoDAO;
import com.infoplacas.model.RequestResponse;
import com.infoplacas.model.Veiculo;

@Stateless
@Path("/veiculo")
public class VeiculoResource {
	@EJB
	private VeiculoDAO veiculoDAO;
	
	/*
	 * Listar Veiculos cadastrados
	 * */
	// URL: http://localhost:8000/InfoPlacas/veiculo
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Veiculo> listarVeiculos() {
		return veiculoDAO.getAllVeiculos();
	}
	
	/*
	 * Buscar Veiculo
	 * */
	// URL: http://localhost:8080/InfoPlacas/veiculo/buscar?placa=ASD-1234
	@POST
	@Path("/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response pesquisarVeiculo(@QueryParam("placa") String placa) {
		// Verifica parametros passados
		if (verificarPlaca(placa)) {
			Veiculo veiculo = veiculoDAO.getVeiculo(placa);
			if (veiculo != null) {
				return Response.status(200).entity(veiculo).build();
			}
			else {
				return Response.status(400).entity(new RequestResponse("Não existe veiculo cadastrado com a placa passada")).build();
			}
		}
		else {
			return Response.status(400).entity(new RequestResponse("Placa com formato invalido")).build();
		}
	}
	
	/*
	 * Cadastrar Veiculo
	 * */
	// URL: http://localhost:8080/InfoPlacas/veiculo
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response criarVeiculo(Veiculo veiculo) throws Exception {
		// Verifica parametros passados
		try {
			String response = verificarVeiculo(veiculo);
			if (response == null) {
				veiculoDAO.salvar(veiculo);
				return Response.status(201).entity(new RequestResponse()).build();
			}
			else {
				return Response.status(400).entity(new RequestResponse(response)).build();
			}
		}
		catch (Exception exception) {
			return Response.status(400).entity(new RequestResponse("Placa duplicada")).build();
		}
	}
	
	/*
	 * Editar Veiculo
	 * */
	// URL: http://localhost:8080/InfoPlacas/veiculo/editar
	@POST
	@Path("/editar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editarVeiculo(Veiculo veiculo) {
		// Verifica parametros passados
		String response = verificarVeiculo(veiculo);
		if (response == null) {
			veiculoDAO.atualizar(veiculo);
			return Response.status(201).entity(new RequestResponse()).build();
		}
		else {
			return Response.status(400).entity(new RequestResponse(response)).build();
		}
	}
	
	/*
	 * Excluir Veiculo
	 * */
	// URL: http://localhost:8080/InfoPlacas/veiculo/excluir?placa=ASD-1234
	@POST
	@Path("/excluir")
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluirVeiculo(@QueryParam("placa") String placa) {
		// Verifica parametros passados
		if (verificarPlaca(placa)) {
			try {
				veiculoDAO.remover(placa);
				return Response.status(200).entity(new RequestResponse()).build();
			}
			catch (Exception exception) {
				return Response.status(400).entity(new RequestResponse(exception.getMessage())).build();
			}
		}
		else {
			return Response.status(400).entity(new RequestResponse("Placa com formato invalido")).build();
		}
	}
	
	
	
	/*
	 * Funcoes de verificacao de parametros
	 * */

	// Verifica o veiculo passado como parametro
	private String verificarVeiculo(Veiculo veiculo) {
        Pattern pattern = Pattern.compile("[A-Z]{3,3}-[0-9]{4,4}");
        Matcher matcher = pattern.matcher(veiculo.getPlaca());
 
        if (veiculo.getPlaca() == null || veiculo.getPlaca().equals("")) {
        	return "Campo placa vazio";
        }
        else if (veiculo.getMarcaModelo() == null || veiculo.getMarcaModelo().equals("")) {
        	return "Campo marcaModelo vazio";
        }
        else if (veiculo.getFabricacaoModelo() == null || veiculo.getFabricacaoModelo().equals("")) {
        	return "Campo fabricacaoModelo vazio";
        }
        else if (veiculo.getLicenciadoAte() == null || veiculo.getLicenciadoAte().equals("")) {
        	return "Campo licenciadoAte vazio";
        }
        else if (!matcher.find()){
            return "Placa com formato invalido";
        }
        else {
            return null;
        }
	}
	
	// Verifica a placa passada como parametro
	private boolean verificarPlaca(String placa) {
		Pattern pattern = Pattern.compile("[A-Z]{3,3}-\\d{4,4}");
        Matcher matcher = pattern.matcher(placa);
 
        if (matcher.find()){
            return true;
        }
        return false;
	}
}