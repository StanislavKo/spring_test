package com.indielemon.logsystem.webboot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.AssertTrue;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.data.repository.Repository;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.indielemon.logsystem.webboot.consts.Consts;
import com.indielemon.logsystem.webboot.controller.ResumeRestController;
import com.indielemon.logsystem.webboot.controller.WelcomeController;
import com.indielemon.logsystem.webboot.controller.pojo.ResumePqgridViewPojo;
import com.indielemon.logsystem.webboot.database.entity.Resume;
import com.indielemon.logsystem.webboot.database.entity.Rubric;
import com.indielemon.logsystem.webboot.database.service.ResumeCustomRepository;
import com.indielemon.logsystem.webboot.database.service.RubricCustomRepository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*; 

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResumeControllerTest {

	private static final Logger logger = Logger.getLogger(ResumeControllerTest.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Logger rootLogger = Logger.getRootLogger();
		rootLogger.setLevel(Level.DEBUG);
//		rootLogger.addAppender(new ConsoleAppender(new PatternLayout("%d{ABSOLUTE} %5p %t %c{1}:%M:%L - %m%n")));
		logger.debug("setUpBeforeClass");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test01_pageData() throws JSONException, IOException {
		List<Resume> resumes = Arrays.asList();
		
		ResumeCustomRepository resumeRepository = mock(ResumeCustomRepository.class);
		when(resumeRepository.findByUserInput("1", null, null)).thenReturn(resumes);
		
		ResumeRestController controller = new ResumeRestController();
		controller.setResumeRepository(resumeRepository);
		ResumePqgridViewPojo pageData = controller.resumes("1", null, null, null);
		
//		assertTrue(pageData.equals("{\"totalRecords\":0,\"curPage\":1,\"data\":[]}"));
		assertTrue(pageData.getCurPage() == 1);
		assertTrue(pageData.getTotalRecords() == 0);
		
		verify(resumeRepository).findByUserInput("1", null, null);
	}

	@Test
	public void test02_pageData() throws JSONException, IOException {
		List<Resume> resumes = Arrays.asList(new Resume(1l, "resumeName01", "resumeHeader01", 10000, null));
		
		ResumeCustomRepository resumeRepository = mock(ResumeCustomRepository.class);
		when(resumeRepository.findByUserInput("1", null, null)).thenReturn(resumes);
		
		ResumeRestController controller = new ResumeRestController();
		controller.setResumeRepository(resumeRepository);
		ResumePqgridViewPojo pageData = controller.resumes("1", null, null, null);
		
//		System.out.println("pageData: " + pageData);
//		assertTrue(pageData.equals("{\"totalRecords\":1,\"curPage\":1,\"data\":[{\"id\":null,\"wantedSalaryRub\":10000,\"name\":\"resumeName01\",\"e1Id\":1,\"rubric\":null,\"header\":\"resumeHeader01\"}]}"));
		assertTrue(pageData.getCurPage() == 1);
		assertTrue(pageData.getTotalRecords() == 1);
		assertTrue(pageData.getData().get(0).getE1Id().equals(1l));
		assertTrue(pageData.getData().get(0).getWantedSalaryRub().equals(10000));
		assertTrue(pageData.getData().get(0).getName().equals("resumeName01"));
		assertTrue(pageData.getData().get(0).getHeader().equals("resumeHeader01"));
		
		verify(resumeRepository).findByUserInput("1", null, null);
	}

}
