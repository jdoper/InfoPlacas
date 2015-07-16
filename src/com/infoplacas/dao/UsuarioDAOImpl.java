package com.infoplacas.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import com.infoplacas.model.Usuario;

@Stateless
public class UsuarioDAOImpl implements UsuarioDAO {

	@PersistenceContext(unitName="info-placas")
	private EntityManager em;
	
	@Override
	public void salvar(Usuario usuario) throws Exception {
		try {
			em.persist(usuario);
		}
		catch (ConstraintViolationException exception) {
			throw new Exception(exception.getMessage());
		}
	}

	@Override
	public Usuario buscarUsuario(Usuario usuario) {
		Usuario resultado = (Usuario) em.createNamedQuery("buscarUsuario")
				.setParameter("login", usuario.getLogin())
				.setParameter("email", usuario.getEmail())
				.setParameter("senha", usuario.getSenha())
				.getSingleResult();
		return resultado;
	}
}
