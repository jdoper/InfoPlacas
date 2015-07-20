package com.infoplacas.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NamedQueries({
	@NamedQuery(name="listaUsuarios", query="SELECT u FROM Usuario u"),
	@NamedQuery(name="buscarUsuario", query="SELECT u FROM Usuario u WHERE u.login = :login AND u.email = :email AND u.senha = :senha")
})

@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = -380133582796170004L;
	private String login;
	private String email;
	private String senha;
	// private Collection<Veiculo> veiculos = new ArrayList<Veiculo>();
	
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
	@Size(min = 8)
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	/*
	 * Relacionamento
	 * */
	/*
	@OneToMany(mappedBy="usuario")
	public Collection<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(Collection<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}
	*/
}
