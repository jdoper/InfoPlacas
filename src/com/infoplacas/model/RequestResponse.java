package com.infoplacas.model;

public class RequestResponse {
	private boolean success;
	private String mensagem;
	
	public RequestResponse() {
		this.success = true;
		mensagem = "";
	}
	
	public RequestResponse(String mensagem) {
		super();
		this.success = false;
		this.mensagem = mensagem;
	}

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
