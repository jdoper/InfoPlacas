package com.infoplacas.resource;

import java.util.List;

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

import com.infoplacas.model.RequestResponse;
import com.infoplacas.model.Usuario;
import com.infoplacas.model.Veiculo;
import com.infoplacas.negocio.InfoPlacas;

@Stateless
@Path("/api")
public class ApplicationResource {
	@EJB
	private InfoPlacas negocio;
	
	/*
	 * ---------------- Usuario ----------------
	 * */
	
	// DESC: Cadastrar Usuario
	// URL: http://localhost:8080/InfoPlacas/api/usuario
	@POST
	@Path("/usuario")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastrarUsuario(Usuario usuario) throws Exception {
		if (negocio.criarUsuario(usuario)) {
			return Response.status(201).entity(new RequestResponse()).build();
		}
		else {
			return Response.status(400).entity(new RequestResponse(negocio.getMensagem())).build();
		}
	}
	
	
	// DESC: Buscar Usuario
	// URL: http://localhost:8080/InfoPlacas/api/usuario/buscar
	@POST
	@Path("/usuario/buscar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response pesquisarUsuario(Usuario usuario) {
		Usuario resultado = negocio.buscarUsuario(usuario);
		if (resultado != null) {
			return Response.status(200).entity(resultado).build();
		}
		else {
			return Response.status(400).entity(new RequestResponse(negocio.getMensagem())).build();
		}
	}
	
	// DESC: Excluir Usuario
	// URL: http://localhost:8080/InfoPlacas/api/usuario/excluir
	@POST
	@Path("/usuario/excluir")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluirUsuario(Usuario usuario) {
		if (negocio.excluirUsuario(usuario)) {
			return Response.status(201).entity(new RequestResponse()).build();
		}
		else {
			return Response.status(400).entity(new RequestResponse(negocio.getMensagem())).build();
		}
	}
	
	
	
	/*
	 * ---------------- Veiculo ----------------
	 * */
	
	// DESC: Listar Veiculos
	// URL: http://localhost:8000/InfoPlacas/api/veiculo
	@GET
	@Path("/veiculo")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Veiculo> listarVeiculos() {
		return negocio.listarVeiculos();
	}
	
	
	// DESC: Buscar Veiculo
	// URL: http://localhost:8080/InfoPlacas/api/veiculo/buscar?placa=ASD-1234
	@POST
	@Path("/veiculo/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response pesquisarVeiculo(@QueryParam("placa") String placa) {
		Veiculo veiculo = negocio.buscarVeiculo(placa);
		if (veiculo != null) {
			return Response.status(200).entity(veiculo).build();
		}
		else {
			return Response.status(400).entity(new RequestResponse(negocio.getMensagem())).build();
		}
	}
	
	
	// DESC: Cadastrar Veiculo
	// URL: http://localhost:8080/InfoPlacas/api/veiculo
	@POST
	@Path("/veiculo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response criarVeiculo(Veiculo veiculo) {
		if (negocio.criarVeiculo(veiculo)) {
			return Response.status(201).entity(new RequestResponse()).build();
		}
		else {
			return Response.status(400).entity(new RequestResponse(negocio.getMensagem())).build();
		}
	}
	
	
	// DESC: Editar Veiculo
	// URL: http://localhost:8080/InfoPlacas/api/veiculo/editar
	@POST
	@Path("/veiculo/editar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editarVeiculo(Veiculo veiculo) {
		if (negocio.editarVeiculo(veiculo)) {
			return Response.status(201).entity(new RequestResponse()).build();
		}
		else {
			return Response.status(400).entity(new RequestResponse(negocio.getMensagem())).build();
		}
	}
	
	
	// DESC: Excluir Veiculo
	// URL: http://localhost:8080/InfoPlacas/api/veiculo/excluir?placa=ASD-1234
	@POST
	@Path("/veiculo/excluir")
	@Produces(MediaType.APPLICATION_JSON)
	public Response excluirVeiculo(@QueryParam("placa") String placa) {
		if (negocio.excluirVeiculo(placa)) {
			return Response.status(201).entity(new RequestResponse()).build();
		}
		else {
			return Response.status(400).entity(new RequestResponse(negocio.getMensagem())).build();
		}
	}
}
