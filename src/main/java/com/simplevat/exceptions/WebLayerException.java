package com.simplevat.exceptions;

import com.simplevat.service.exceptions.BaseException;

public class WebLayerException extends BaseException {

	
	private String errorMsg;
	private WebLayerErrorCode errorCode;
	public static final String WEB = "WEB";
	
	public WebLayerException(String errorMsg_, WebLayerErrorCode errorCode_) {
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
		return WEB;
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
