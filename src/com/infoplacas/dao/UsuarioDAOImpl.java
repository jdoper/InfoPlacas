package com.infoplacas.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import com.infoplacas.model.Usuario;


@Stateless
public class UsuarioDAOImpl implements UsuarioDAO {

	@PersistenceContext(unitName="info-placas")
	private EntityManager em;
	
	/*
	 * Cria um novo registro de Veiculo
	 * */
	@Override
	public void salvar(Usuario usuario) throws Exception {
		try {
			if (buscarUsuario(usuario) == null) {
				em.persist(usuario);
			}
			else {
				throw new Exception("Login existente");
			}
		}
		catch (ConstraintViolationException exception) {
			throw new Exception("Senha deve ter no minimo 8 digitos");
		}
		catch (PersistenceException exception) {
			throw new Exception("Login existente");
		}
	}

	/*
	 * Verifica se existe um registro de usuário com os parametros passados
	 * */
	@Override
	public Usuario buscarUsuario(Usuario usuario) {
		Usuario resultado = em.find(Usuario.class, usuario.getEmail());
		if (resultado != null &&
			resultado.getEmail() == usuario.getEmail() &&
			resultado.getNome() == usuario.getNome()) {
			return resultado;
		}
		else {
			return null;
		}
	}
	
	/*
	 * Remove usuario passado
	 * */
	@Override
	public void remover(Usuario usuario) throws Exception {
		try {
			Usuario remover = em.find(Usuario.class, usuario.getEmail());
			if (remover != null) {
				em.remove(remover);
			}
			else {
				throw new Exception("Usuário não encontrado");
			}
		}
		catch (IllegalArgumentException exception) {
			throw new Exception("Usuário não encontrado");
		}
	}
}
