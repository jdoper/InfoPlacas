package com.infoplacas.resource;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import com.infoplacas.dao.VeiculoDAO;
import com.infoplacas.model.RequestResponse;
import com.infoplacas.model.Veiculo;

@Stateless
@Path("/veiculo")
public class VeiculoResource {
	@EJB
	private VeiculoDAO veiculoDAO;
	
	/*
	 * GetAll
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
		Veiculo veiculo = veiculoDAO.getVeiculo(placa);
		return Response.status(200).entity(veiculo).build();
	}
	
	/*
	 * Cadastrar Veiculo
	 * */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response criarVeiculo(Veiculo veiculo) {
		veiculoDAO.salvar(veiculo);
		return Response.status(201).entity(new RequestResponse()).build();
	}
	
	/*
	 * Editar Veiculo
	 * */
	@POST
	@Path("/editar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editarVeiculo(Veiculo veiculo) {
		veiculoDAO.atualizar(veiculo);
		return Response.status(200).entity(new RequestResponse()).build();
	}
	
	/*
	 * Excluir Veiculo
	 * */
	@POST
	@Path("/excluir")
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluirVeiculo(@QueryParam("placa") String placa) {
		veiculoDAO.remover(placa);
		return Response.status(200).entity(new RequestResponse()).build();
	}
}