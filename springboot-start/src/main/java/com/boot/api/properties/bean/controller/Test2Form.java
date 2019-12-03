package com.boot.api.properties.bean.controller;

import javax.validation.constraints.NotBlank;

public class Test2Form {
	
	@NotBlank(message = "id不能为空。")
	public String id;
	
	@NotBlank(message = "name不能为空。")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
