package com.indielemon.logsystem.webboot.controller;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.indielemon.logsystem.webboot.consts.Consts;
import com.indielemon.logsystem.webboot.controller.pojo.ResumePojo;
import com.indielemon.logsystem.webboot.database.entity.Resume;
import com.indielemon.logsystem.webboot.database.entity.Rubric;
import com.indielemon.logsystem.webboot.database.service.RubricCustomRepository;

@Controller
public class WelcomeController {

	static final Logger logger = Logger.getLogger(WelcomeController.class);
	
	@Autowired
	private RubricCustomRepository rubricCustomRepository;
	
	public void setRubricRepository(RubricCustomRepository rubricRepository) {
		this.rubricCustomRepository = rubricRepository;
	}

	@RequestMapping("/welcome")
	public ModelAndView welcome() {
		logger.info("welcome");
//		return new ModelAndView("/WEB-INF/jsp/welcome.jsp");
		
		List<Rubric> rootRubrics = rubricCustomRepository.findRoot();

		ModelAndView modelAndView = new ModelAndView("welcome");
		modelAndView.addObject("rubrics", getRootJsonRubric(rootRubrics));
		return modelAndView;
	}

	@RequestMapping("/{anyUrl}")
	public ModelAndView resumeList(@PathVariable String anyUrl) throws IOException {
		logger.info("resumeList [anyUrl:" + anyUrl + "]");

		List<Rubric> rootRubrics = rubricCustomRepository.findRoot();

		ModelAndView modelAndView = new ModelAndView("resumes");
		modelAndView.addObject("totalWidth900", "900");
		modelAndView.addObject("nameWidth250", "250");
		modelAndView.addObject("headerWidth400", "400");
		modelAndView.addObject("salaryWidth150", "150");
		modelAndView.addObject("rubrics", getRootJsonRubric(rootRubrics));
		return modelAndView;
	}

	@RequestMapping("/admin")
	public ModelAndView adminResumeList() throws IOException {
		logger.info("adminResumeList");

		List<Rubric> rootRubrics = rubricCustomRepository.findRoot();

		ModelAndView modelAndView = new ModelAndView("admin");
		modelAndView.addObject("totalWidth900", "700");
		modelAndView.addObject("nameWidth250", "150");
		modelAndView.addObject("headerWidth400", "350");
		modelAndView.addObject("salaryWidth150", "75");
		modelAndView.addObject("rubrics", getRootJsonRubric(rootRubrics));
		modelAndView.addObject("resume", new ResumePojo());
		return modelAndView;
	}
	
	private JSONObject getRootJsonRubric(List<Rubric> rootRubrics) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(Consts.RUBRIC_JSON);
			JSONArray jsonRootRubricsArray = jsonObject.getJSONObject(Consts.RUBRIC_JSON_CORE).getJSONArray(Consts.RUBRIC_JSON_DATA);
			for (Rubric rootRubric : rootRubrics) {
				jsonRootRubricsArray.put(getJsonRubric(rootRubric));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	private Object getJsonRubric(Rubric rubric) throws JSONException {
		JSONObject jsonRubric = new JSONObject();
		if (!rubric.getChildren().isEmpty()) {
			JSONArray children = new JSONArray();
			for (Rubric childRubric : rubric.getChildren()) {
				children.put(getJsonRubric(childRubric));
			}
			jsonRubric.put("children", children);
		}
		jsonRubric.put("text", rubric.getName());
		jsonRubric.put("e1Id", rubric.getE1Id());
		logger.debug("    text:" + rubric.getName());
		return jsonRubric;
	}
	
}
