package com.infoplacas.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@NamedQueries({
	@NamedQuery(name="buscarUsuario", query="SELECT u FROM Usuario u WHERE u.login = :login AND u.email = :email AND u.senha = :senha")
})

@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private String login;
	private String email;
	private String senha;
	
	public Usuario() {
		super();
	}

	public Usuario(String login, String email, String senha) {
		super();
		this.login = login;
		this.email = email;
		this.senha = senha;
	}

	@Id
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@NotNull(message="Email não inserido")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotNull(message="Senha não inserida")
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}