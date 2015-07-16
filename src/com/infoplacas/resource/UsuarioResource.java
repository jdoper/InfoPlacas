package com.infoplacas.resource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.infoplacas.dao.UsuarioDAO;
import com.infoplacas.model.RequestResponse;
import com.infoplacas.model.Usuario;

@Stateless
@Path("/usuario")
public class UsuarioResource {
	@EJB
	private UsuarioDAO usuarioDAO;
	
	/*
	 * Cadastrar Usuário
	 * */
	// URL: http://localhost:8080/InfoPlacas/usuario
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastrarUsuario(Usuario usuario) throws Exception {
		String response = verificarUsuario(usuario);
		if (response == null) {
			usuarioDAO.salvar(usuario);
			return Response.status(201).entity(new RequestResponse()).build();
		}
		else {
			return Response.status(400).entity(new RequestResponse(response)).build();
		}
	}
	
	/*
	 * Buscar Usuario
	 * */
	// URL: http://localhost:8080/InfoPlacas/usuario/buscar
	@POST
	@Path("/buscar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response pesquisarVeiculo(Usuario usuario) {
		Usuario resultado = usuarioDAO.buscarUsuario(usuario);
		if (resultado != null) {
			return Response.status(200).entity(resultado).build();
		}
		else {
			return Response.status(400).entity(new RequestResponse("Usuário não existe")).build();
		}
	}
	
	
	/*
	 * Funçoes para verificar parametros
	 * */

	// Verifica os parametros do Veiculo passado
	private String verificarUsuario(Usuario usuario) {
		Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(usuario.getEmail());
 
        if (usuario.getLogin() == null || usuario.getLogin().equals("")) {
        	return "Campo login vazio";
        }
        else if (usuario.getEmail() == null || usuario.getEmail().equals("")) {
        	return "Campo email vazio";
        }
        else if (usuario.getSenha() == null || usuario.getSenha().equals("")) {
        	return "Campo senha vazio";
        }
        else if (!matcher.find()){
            return "Email com formato invalido";
        }
        else {
            return null;
        }
	}
}
