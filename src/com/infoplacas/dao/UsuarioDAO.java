package com.infoplacas.dao;

import javax.ejb.Local;

import com.infoplacas.model.Usuario;

@Local
public interface UsuarioDAO {
	
	public void salvar(Usuario usuario) throws Exception;
	
	public Usuario buscarUsuario(Usuario usuario);
}
