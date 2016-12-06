package com.indielemon.logsystem.webboot.controller.pojo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.BindingResult;

public class ResumeViewPojo {

	private String msg;
	private Map<String, List<String>> bindingResult;
	
	public ResumeViewPojo() {
		super();
	}

	public ResumeViewPojo(String msg, Map<String, List<String>> bindingResult) {
		super();
		this.msg = msg;
		this.bindingResult = bindingResult;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, List<String>> getBindingResult() {
		return bindingResult;
	}

	public void setBindingResult(Map<String, List<String>> bindingResult) {
		this.bindingResult = bindingResult;
	}
	
}
