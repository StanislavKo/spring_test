package com.indielemon.logsystem.webboot.database.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
public class Rubric {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "e1_id", unique=true)
	private Long e1Id;

	@Column(name = "name")
	private String name;

	@ManyToOne
	private Rubric parent;

	@OneToMany(mappedBy = "parent", fetch=FetchType.EAGER)
	private Collection<Rubric> children;

	protected Rubric() {
	}

	public Rubric(Long e1Id, String name) {
		this.e1Id = e1Id;
		this.name = name;
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

	public Rubric getParent() {
		return parent;
	}

	public void setParent(Rubric parent) {
		this.parent = parent;
	}

	public Collection<Rubric> getChildren() {
		return children;
	}

	public void setChildren(Collection<Rubric> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return String.format("Customer[id=%d, e1Id='%s', name='%s']", id, e1Id, name);
	}

}