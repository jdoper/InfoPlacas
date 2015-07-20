package com.infoplacas.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NamedQueries({
	@NamedQuery(name="listaVeiculos", query="SELECT v FROM Veiculo v")
})

@Entity
public class Veiculo implements Serializable {
	private static final long serialVersionUID = 854477184576137624L;
	private String placa;
	private String marcaModelo;
	private String fabricacaoModelo;
	private String licenciadoAte;
	private float multas;
	private float ipva;
	private float taxasDetran;
	private float seguroDPVAT;
	private String observacoes;
	// private Usuario usuario;
	
	public Veiculo() {
		super();
	}

	public Veiculo(String placa, String marcaModelo, String fabricacaoModelo,
			String licenciadoAte, float multas, float ipva, float taxasDetran,
			float seguroDPVAT, String observacoes) {
		super();
		this.placa = placa;
		this.marcaModelo = marcaModelo;
		this.fabricacaoModelo = fabricacaoModelo;
		this.licenciadoAte = licenciadoAte;
		this.multas = multas;
		this.ipva = ipva;
		this.taxasDetran = taxasDetran;
		this.seguroDPVAT = seguroDPVAT;
		this.observacoes = observacoes;
	}

	@Id
	@Pattern(regexp="[A-Z]{3}-[0-9]{4}", 
			message="Placa com formato invalido")
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	@NotNull(message="Especifique a marca/modelo do veiculo")
	public String getMarcaModelo() {
		return marcaModelo;
	}

	public void setMarcaModelo(String marcaModelo) {
		this.marcaModelo = marcaModelo;
	}

	@NotNull(message="Especifique o ano de fabricacao do veiculo")
	public String getFabricacaoModelo() {
		return fabricacaoModelo;
	}

	public void setFabricacaoModelo(String fabricacaoModelo) {
		this.fabricacaoModelo = fabricacaoModelo;
	}

	@NotNull(message="Especifique o ano de validade da licenca veicular")
	public String getLicenciadoAte() {
		return licenciadoAte;
	}

	public void setLicenciadoAte(String licenciadoAte) {
		this.licenciadoAte = licenciadoAte;
	}

	public float getMultas() {
		return multas;
	}

	public void setMultas(float multas) {
		this.multas = multas;
	}

	public float getIpva() {
		return ipva;
	}

	public void setIpva(float ipva) {
		this.ipva = ipva;
	}

	public float getTaxasDetran() {
		return taxasDetran;
	}

	public void setTaxasDetran(float taxasDetran) {
		this.taxasDetran = taxasDetran;
	}

	public float getSeguroDPVAT() {
		return seguroDPVAT;
	}

	public void setSeguroDPVAT(float seguroDPVAT) {
		this.seguroDPVAT = seguroDPVAT;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	/*
	 * Relacionamento
	 * */
	/*
	@ManyToOne
	@JoinColumn(name="usuario_login") 
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	*/
}
