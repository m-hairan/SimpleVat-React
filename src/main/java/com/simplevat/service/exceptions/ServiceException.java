package com.simplevat.service.exceptions;

public class ServiceException extends BaseException {

	private String errorMsg;
	private ServiceErrorCode errorCode;
	public static final String SERVICE = "SERVICE";
	public ServiceException(String errorMsg_, ServiceErrorCode errorCode_) {
		super(errorMsg_);
		this.errorMsg = errorMsg_;
		this.errorCode = errorCode_;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getSource() {
		return SERVICE;
	}

	@Override
	public String getErrorCode() {
		return this.errorCode.name();
	}

	@Override
	public String getErrorDescription() {
		return this.errorMsg + ", " + this.errorCode.getErrorDescription();
	}

	@Override
	public boolean isBusinessException() {
		return this.errorCode.isBusinessException();
	}

}
