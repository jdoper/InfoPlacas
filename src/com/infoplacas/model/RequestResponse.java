package com.infoplacas.model;


public class RequestResponse {
	private boolean success;
	private String mensagem;
	
	/*
	 * Construtores
	 * */
	
	// Mensagem de sucesso
	public RequestResponse() {
		this.success = true;
		mensagem = "";
	}
	
	// Mensagem de erro
	public RequestResponse(String mensagem) {
		super();
		this.success = false;
		this.mensagem = mensagem;
	}
	
	// Generico
	public RequestResponse(boolean success, String mensagem) {
		super();
		this.success = success;
		this.mensagem = mensagem;
	}

	
	/*
	 * Acessadores
	 * */
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
