package com.infoplacas.dao;

import java.util.List;

import javax.ejb.Local;

import com.infoplacas.model.Usuario;

@Local
public interface UsuarioDAO {
	
	public void salvar(Usuario usuario) throws Exception;
	
	public List<Usuario> buscarUsuario(Usuario usuario);
	
	public void remover(Usuario usuario) throws Exception;
}
