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
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Veiculo> listarVeiculos() {
		return veiculoDAO.getAllVeiculos();
	}
	
	/*
	 * Buscar Veiculo
	 * */
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
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response criarVeiculo(Veiculo veiculo) {
		// Verifica parametros passados
		String response = verificarVeiculo(veiculo);
		if (response == null) {
			veiculoDAO.salvar(veiculo);
			return Response.status(201).entity(new RequestResponse()).build();
		}
		else {
			return Response.status(400).entity(new RequestResponse(response)).build();
		}
	}
	
	/*
	 * Editar Veiculo
	 * */
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
	@POST
	@Path("/excluir")
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluirVeiculo(@QueryParam("placa") String placa) {
		// Verifica parametros passados
		if (verificarPlaca(placa)) {
			if (veiculoDAO.remover(placa)) {
				return Response.status(200).entity(new RequestResponse()).build();
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
	 * Funçoes para verificar parametros
	 * */

	// Verifica os parametros do Veiculo passado
	private String verificarVeiculo(Veiculo veiculo) {
        Pattern pattern = Pattern.compile("[A-Z]{3,3}-[0-9]{4,4}");
        Matcher matcher = pattern.matcher(veiculo.getPlaca());
 
        if (!matcher.find()){
            return "Placa com formato invalido";
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
        else {
            return null;
        }
	}
	
	// Verifica o parametro placa passado
	private boolean verificarPlaca(String placa) {
		Pattern pattern = Pattern.compile("[A-Z]{3,3}-\\d{4,4}");
        Matcher matcher = pattern.matcher(placa);
 
        if (matcher.find()){
            return true;
        }
        return false;
	}
}