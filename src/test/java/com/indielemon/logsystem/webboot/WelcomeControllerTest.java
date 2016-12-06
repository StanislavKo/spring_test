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
import com.indielemon.logsystem.webboot.controller.WelcomeController;
import com.indielemon.logsystem.webboot.database.entity.Rubric;
import com.indielemon.logsystem.webboot.database.service.RubricCustomRepository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*; 

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WelcomeControllerTest {

	private static final Logger logger = Logger.getLogger(WelcomeControllerTest.class);

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
	public void test01_resumeList() throws JSONException, IOException {
		List<Rubric> rootRubrics = Arrays.asList();
		JSONObject jsonObject = new JSONObject(Consts.RUBRIC_JSON);
		
		RubricCustomRepository rubricRepository = mock(RubricCustomRepository.class);
		when(rubricRepository.findRoot()).thenReturn(rootRubrics);
		
		WelcomeController controller = new WelcomeController();
		controller.setRubricRepository(rubricRepository);
		ModelAndView mv = controller.resumeList("1");
		
		assertTrue(((String) mv.getModel().get("totalWidth900")).equals("900"));
		assertTrue(((String) mv.getModel().get("nameWidth250")).equals("250"));
		assertTrue(((String) mv.getModel().get("headerWidth400")).equals("400"));
		assertTrue(((String) mv.getModel().get("salaryWidth150")).equals("150"));
		assertTrue(((JSONObject) mv.getModel().get("rubrics")).toString().equals(jsonObject.toString()));
		
		verify(rubricRepository).findRoot();
	}

	@Test
	public void test02_resumeList() throws JSONException, IOException {
		Rubric rubric = new Rubric(1L, "IT");
		rubric.setChildren(new ArrayList<Rubric>());
		List<Rubric> rootRubrics = Arrays.asList(new Rubric[] {rubric});
		JSONObject jsonObject = new JSONObject(Consts.RUBRIC_JSON);
		
		RubricCustomRepository rubricRepository = mock(RubricCustomRepository.class);
		when(rubricRepository.findRoot()).thenReturn(rootRubrics);
		
		WelcomeController controller = new WelcomeController();
		controller.setRubricRepository(rubricRepository);
		ModelAndView mv = controller.resumeList("1");
		
		assertTrue(((String) mv.getModel().get("totalWidth900")).equals("900"));
		assertTrue(((String) mv.getModel().get("nameWidth250")).equals("250"));
		assertTrue(((String) mv.getModel().get("headerWidth400")).equals("400"));
		assertTrue(((String) mv.getModel().get("salaryWidth150")).equals("150"));
		assertTrue(((JSONObject) mv.getModel().get("rubrics")).toString().equals("{\"core\":{\"data\":[{\"text\":\"IT\",\"e1Id\":1}]}}"));
		
		verify(rubricRepository).findRoot();
	}

	@Test
	public void test03_resumeList() throws JSONException, IOException {
		Rubric rubric = new Rubric(1L, "IT");
		Rubric chiclRubric = new Rubric(20L, "Database Administration");
		rubric.setChildren(Arrays.asList(new Rubric[] {chiclRubric}));
		chiclRubric.setChildren(new ArrayList<Rubric>());
		List<Rubric> rootRubrics = Arrays.asList(new Rubric[] {rubric});
		JSONObject jsonObject = new JSONObject(Consts.RUBRIC_JSON);
		
		RubricCustomRepository rubricRepository = mock(RubricCustomRepository.class);
		when(rubricRepository.findRoot()).thenReturn(rootRubrics);
		
		WelcomeController controller = new WelcomeController();
		controller.setRubricRepository(rubricRepository);
		ModelAndView mv = controller.resumeList("1");
		
		assertTrue(((String) mv.getModel().get("totalWidth900")).equals("900"));
		assertTrue(((String) mv.getModel().get("nameWidth250")).equals("250"));
		assertTrue(((String) mv.getModel().get("headerWidth400")).equals("400"));
		assertTrue(((String) mv.getModel().get("salaryWidth150")).equals("150"));
		assertTrue(((JSONObject) mv.getModel().get("rubrics")).toString().equals("{\"core\":{\"data\":[{\"text\":\"IT\",\"children\":[{\"text\":\"Database Administration\",\"e1Id\":20}],\"e1Id\":1}]}}"));
		
		verify(rubricRepository).findRoot();
	}

}
