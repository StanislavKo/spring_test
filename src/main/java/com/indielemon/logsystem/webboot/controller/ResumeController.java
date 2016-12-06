package com.indielemon.logsystem.webboot.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indielemon.logsystem.webboot.consts.Consts;
import com.indielemon.logsystem.webboot.controller.pojo.ResumePojo;
import com.indielemon.logsystem.webboot.controller.pojo.ResumeViewPojo;
import com.indielemon.logsystem.webboot.database.entity.Resume;
import com.indielemon.logsystem.webboot.database.entity.Rubric;
import com.indielemon.logsystem.webboot.database.service.ResumeRepository;
import com.indielemon.logsystem.webboot.database.service.RubricCustomRepository;
import com.indielemon.logsystem.webboot.database.service.RubricRepository;

@Controller
@RequestMapping("/rubrics")
public class ResumeController {

	static final Logger logger = Logger.getLogger(ResumeController.class);

	@Autowired
	public MessageSource messageSource;

	@Autowired
	private ResumeRepository resumeRepository;

	@Autowired
	private RubricRepository rubricRepository;

	@RequestMapping(value = "/newResume", method = RequestMethod.POST)
	public String newResume(@ModelAttribute("resume") @Valid ResumePojo resumePojo, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		logger.info("newResume [resumePojo:" + resumePojo + "]");

		if (bindingResult.hasErrors()) {
			for (Object object : bindingResult.getAllErrors()) {
				if (object instanceof FieldError) {
					FieldError fieldError = (FieldError) object;
					logger.error("FieldError " + fieldError.getCode());
				}
				if (object instanceof ObjectError) {
					ObjectError objectError = (ObjectError) object;
					logger.error("ObjectError " + objectError.getCode());
				}
			}
			logger.info("newResume before hasErrors redirect");
			
			String msg = messageSource.getMessage("new_resume_added_failure", null, LocaleContextHolder.getLocale());
			Map<String, List<String>> bindingResultMap = new HashMap();
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				List<String> fieldErrors = bindingResultMap.get(fieldError.getField());
				if (fieldErrors == null) {
					fieldErrors = new LinkedList();
					bindingResultMap.put(fieldError.getField(), fieldErrors);
				}
				fieldErrors.add(fieldError.getDefaultMessage());
			}
			ResumeViewPojo resumeViewPojo = new ResumeViewPojo(msg, bindingResultMap);

			ObjectMapper mapper = new ObjectMapper();
			try {
				//Object to JSON in String
				String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resumeViewPojo);
				model.addAttribute("resumeViewPojo", jsonInString);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
//			redirectAttributes.addFlashAttribute("msg", messageSource.getMessage("new_resume_added_failure", null, LocaleContextHolder.getLocale()));
			return "rubrics/newResume";
		} else {
			Rubric rubric = rubricRepository.findByE1Id(resumePojo.getRubricId());
			Resume resume = new Resume(resumePojo.getE1Id(), resumePojo.getName(), resumePojo.getHeader(), resumePojo.getWantedSalaryRub(), rubric);
			resumeRepository.save(resume);

			ResumeViewPojo resumeViewPojo = new ResumeViewPojo(messageSource.getMessage("new_resume_added_successfully", null, LocaleContextHolder.getLocale()), null);

			ObjectMapper mapper = new ObjectMapper();
			try {
				//Object to JSON in String
				String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resumeViewPojo);
				model.addAttribute("resumeViewPojo", jsonInString);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
//			redirectAttributes.addFlashAttribute("msg", messageSource.getMessage("new_resume_added_successfully", null, LocaleContextHolder.getLocale()));
			return "rubrics/newResume";
		}
	}

}
