package com.infoplacas.dao;

import java.util.List;

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
	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> buscarUsuario(Usuario usuario) {
		List<Usuario> resultado = em.createNamedQuery("buscarUsuario")
				.setParameter("login", usuario.getLogin())
				.setParameter("email", usuario.getEmail())
				.setParameter("senha", usuario.getSenha()).getResultList();
		return resultado;
	}
	
	/*
	 * Remove usuario passado
	 * */
	@Override
	public void remover(Usuario usuario) throws Exception {
		try {
			em.remove(usuario);
		}
		catch (IllegalArgumentException exception) {
			throw new Exception("Usuário não encontrado");
		}
	}
}
