package com.indielemon.logsystem.webboot.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Resume {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "e1_id")
	private Long e1Id;

	@Column(name = "name")
	private String name;

	@Column(name = "header")
	private String header;

	@Column(name = "wanted_salary_rub")
	private Integer wantedSalaryRub;

	@ManyToOne(fetch=FetchType.LAZY)
	private Rubric rubric;

	public Resume() {
	}

	public Resume(Long e1Id, String name, String header, Integer wantedSalaryRub, Rubric rubric) {
		this.e1Id = e1Id;
		this.name = name;
		this.header = header;
		this.wantedSalaryRub = wantedSalaryRub;
		this.rubric = rubric;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Rubric getRubric() {
		return rubric;
	}

	public void setRubric(Rubric rubric) {
		this.rubric = rubric;
	}

	@Override
	public String toString() {
		return String.format("Customer[id=%d, e1Id='%s', wantedSalaryRub='%s']", id, e1Id, wantedSalaryRub);
	}

}