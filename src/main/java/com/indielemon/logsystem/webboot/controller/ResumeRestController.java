package com.indielemon.logsystem.webboot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;
import com.indielemon.logsystem.webboot.controller.pojo.ResumePqgridViewPojo;
import com.indielemon.logsystem.webboot.controller.pojo.ResumePojo;
import com.indielemon.logsystem.webboot.database.entity.Resume;
import com.indielemon.logsystem.webboot.database.entity.Rubric;
import com.indielemon.logsystem.webboot.database.service.ResumeCustomRepository;
import com.indielemon.logsystem.webboot.database.service.ResumeRepository;
import com.indielemon.logsystem.webboot.database.service.RubricRepository;
import com.indielemon.logsystem.webboot.database.service.RubricCustomRepository;

@RestController
@RequestMapping("/rubrics")
public class ResumeRestController {

	static final Logger logger = Logger.getLogger(WelcomeController.class);
	
	@Autowired
	private ResumeCustomRepository resumeCustomRepository;
	
	public void setResumeRepository(ResumeCustomRepository resumeRepository) {
		this.resumeCustomRepository = resumeRepository;
	}

//	@Secured("hrs")
//	@PreAuthorize("hasRole('hrs') or hasRole('managers') or hasRole('submanagers')")
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/{rubricId}/resumes", headers={"Accept=text/xml, application/json"}/*, produces = MediaType.APPLICATION_JSON_VALUE*/ /*, params = {"pq_datatype, pq_filter, _"}*/)
	public @ResponseBody ResumePqgridViewPojo resumes(@PathVariable String rubricId, @RequestParam(value = "pq_datatype", required = false) String pq_datatype
			, @RequestParam(value = "pq_filter", required = false) String pq_filter, @RequestParam(value = "_", required = false) String ts) throws IOException {
		logger.info("pageData [rubricId:" + rubricId + "] [pq_filter:" + pq_filter + "]");

		try {
			String wantedSalaryFilter = null;
			String headerFilter = null;
			if (pq_filter != null) {
				JSONObject pqFilterObj = new JSONObject(pq_filter);
				for (int i = 0; i < pqFilterObj.getJSONArray("data").length(); i++) {
					JSONObject pqFilter = pqFilterObj.getJSONArray("data").getJSONObject(i);
					switch (pqFilter.getString("dataIndx")) {
					case "header":
						headerFilter = pqFilter.getString("value");
						break;
					case "wantedSalaryRub":
						wantedSalaryFilter = pqFilter.getString("value");
						break;
					}
				}
			}

			List<Resume> resumes = new LinkedList<Resume>();
			resumes = resumeCustomRepository.findByUserInput(rubricId, headerFilter, wantedSalaryFilter);
			for (Resume resume : resumes) {
				resume.setRubric(null);
			}
			
//			String sb = "{\"totalRecords\":" + resumes.size() + ",\"curPage\":1,\"data\":" + new JSONArray(resumes).toString() + "}";
//			return sb.toString();
			return new ResumePqgridViewPojo(resumes.size(), 1, resumes);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResumePqgridViewPojo(0, 1, new ArrayList<Resume>());
		}
	}
	
}
