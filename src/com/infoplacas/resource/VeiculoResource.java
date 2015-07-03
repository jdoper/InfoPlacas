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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.infoplacas.dao.VeiculoDAO;
import com.infoplacas.model.Veiculo;

@Stateless
@Path("/veiculo")
public class VeiculoResource {
	@EJB
	private VeiculoDAO veiculoDAO;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Veiculo> listarVeiculos() {
		return veiculoDAO.getAllVeiculos();
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response pesquisarVeiculo(@FormParam("placa") String placa) {
		Veiculo veiculo = veiculoDAO.getVeiculo(placa);
		return Response.ok(veiculo).build();
	}
	
	/*
	 * Criar Veiculo
	 * */
	
	/*
	 * Editar Veiculo
	 * */
	
	@POST
	@Path("/excluir")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluirVeiculo(@FormParam("placa") String placa) {
		veiculoDAO.remover(placa);
		return Response.ok(placa).build();
	}
}