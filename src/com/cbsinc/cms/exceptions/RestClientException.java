package com.cbsinc.cms.exceptions;

public class RestClientException extends java.lang.Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String errorCode = "";
	private String errorMessage = "";

	public RestClientException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RestClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public RestClientException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RestClientException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public RestClientException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public RestClientException(String errorCode, String errorMessage, Throwable cause) {
		super(errorMessage + " code: " + errorCode, cause);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public RestClientException(String errorCode, String errorMessage) {
		super(errorMessage + " code: " + errorCode);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "RestClientException [errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
	}

}
