package com.indielemon.logsystem.webboot.controller.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

public class ResumePojo {

	@NotNull
	private Long e1Id;

	@NotNull
	private String name;

	@NotNull
	private String header;

	@NotNull
	private Integer wantedSalaryRub;

	@NotNull
	private Long rubricId;

	public ResumePojo() {
	}

	public Long getE1Id() {
		return e1Id;
	}

	public void setE1Id(Long e1Id) {
		this.e1Id = e1Id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Integer getWantedSalaryRub() {
		return wantedSalaryRub;
	}

	public void setWantedSalaryRub(Integer wantedSalaryRub) {
		this.wantedSalaryRub = wantedSalaryRub;
	}

	public Long getRubricId() {
		return rubricId;
	}

	public void setRubricId(Long rubricId) {
		this.rubricId = rubricId;
	}

	@Override
	public String toString() {
		return "ResumeViewPojo [e1Id=" + e1Id + ", name=" + name + ", header=" + header + ", wantedSalaryRub=" + wantedSalaryRub + ", rubricId=" + rubricId
				+ "]";
	}


}