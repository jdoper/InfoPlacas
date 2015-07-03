package com.infoplacas.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@NamedQueries({
	@NamedQuery(name="listaVeiculos", query="SELECT v FROM Veiculo v ORDER BY v.dataCadastro DESC")
})

@Entity
public class Veiculo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String placa;
	private String marcaModelo;
	private String fabricacaoModelo;
	private String licenciadoAte;
	private float multas;
	private float ipva;
	private float taxasDetran;
	private float seguroDPVAT;
	private String observacoes;
	private Date dataCadastro;
	
	public Veiculo() {
		super();
	}

	public Veiculo(String placa, String marcaModelo, String fabricacaoModelo,
			String licenciadoAte, float multas, float ipva, float taxasDetran,
			float seguroDPVAT, String observacoes, Date dataCadastro) {
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
		this.dataCadastro = dataCadastro;
	}

	@Id
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	@NotNull
	public String getMarcaModelo() {
		return marcaModelo;
	}

	public void setMarcaModelo(String marcaModelo) {
		this.marcaModelo = marcaModelo;
	}

	@NotNull
	public String getFabricacaoModelo() {
		return fabricacaoModelo;
	}

	public void setFabricacaoModelo(String fabricacaoModelo) {
		this.fabricacaoModelo = fabricacaoModelo;
	}

	@NotNull
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

	@Temporal(value=TemporalType.TIMESTAMP)
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	@Override
	public String toString() {
		return "Veiculo ["
				+ "placa=" + placa
				+ ", marcaModelo=" + marcaModelo
				+ ", fabricacaoModelo=" + fabricacaoModelo
				+ ", licenciadoAte=" + licenciadoAte
				+ ", multas=" + multas
				+ ", ipva=" + ipva
				+ ", taxasDetran=" + taxasDetran
				+ ", seguroDPVAT=" + seguroDPVAT
				+ ", observacoes=" + observacoes
				+ ", dataCadastro=" + dataCadastro
				+ "]";
	}
}
