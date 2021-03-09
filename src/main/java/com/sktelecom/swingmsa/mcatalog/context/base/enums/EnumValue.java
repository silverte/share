package com.sktelecom.swingmsa.mcatalog.context.base.enums;

import lombok.Getter;

@Getter
public class EnumValue {
	private String key;
	private String value;
	private String ref;
	
	public EnumValue(EnumModel enumModel) {
		this.key = enumModel.getKey();
		this.value = enumModel.getValue();
		this.ref = enumModel.getRef();
	}
	
	public EnumValue() {
		this.key = "";
		this.value = "";
		this.ref = "";
	}
}
