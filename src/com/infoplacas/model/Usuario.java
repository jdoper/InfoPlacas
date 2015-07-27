package com.infoplacas.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@NamedQueries({
	@NamedQuery(name="buscarUsuario", query="SELECT u FROM Usuario u WHERE u.nome = :nome AND u.email = :email AND u.senha = :senha"),
	@NamedQuery(name="buscarUsuarioByEmail", query="SELECT u FROM Usuario u WHERE u.email = :email"),
})

@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = -380133582796170004L;
	private String email;
	private String nome;
	private String senha;
	
	public Usuario() {
		super();
	}

	public Usuario(String email, String nome, String senha) {
		super();
		this.email = email;
		this.nome = nome;
		this.senha = senha;
	}

	@Id
	@NotNull(message="Email nao inserido")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotNull(message="Nome nao inserido")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Size(min = 8)
	@NotNull(message="Senha nao inserida")
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
