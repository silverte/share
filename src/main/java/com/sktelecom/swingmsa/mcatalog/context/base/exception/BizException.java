package com.sktelecom.swingmsa.mcatalog.context.base.exception;

import lombok.Getter;

@Getter
public class BizException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5606105487641408112L;
	
	private Exception exception;
	
	public BizException(Exception e) {	
		super(e);
		this.exception = e;
	}
}
