package com.indielemon.logsystem.webboot.controller.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.indielemon.logsystem.webboot.database.entity.Resume;

@XmlRootElement(name = "resumePqgridViewPojo")
public class ResumePqgridViewPojo {

	private Integer totalRecords;
	private Integer curPage;
	private List<Resume> data;

	public ResumePqgridViewPojo() {
		super();
	}

	public ResumePqgridViewPojo(Integer totalRecords, Integer curPage, List<Resume> data) {
		super();
		this.totalRecords = totalRecords;
		this.curPage = curPage;
		this.data = data;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	@XmlElement
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Integer getCurPage() {
		return curPage;
	}

	@XmlElement
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public List<Resume> getData() {
		return data;
	}

	@XmlElement
	public void setData(List<Resume> data) {
		this.data = data;
	}

}
