package com.indielemon.logsystem.webboot.consts;

import java.text.SimpleDateFormat;

public interface Consts {

//	SimpleDateFormat LOG_FILE_TIME_FORMAT = new SimpleDateFormat("yyyy.MM.dd HH:mm");
//	
//	String DYNAMODB_LOGFILE_APP = "APP_NAME";
//	String DYNAMODB_LOGFILE_STAGE = "STAGE_NAME";
//	String DYNAMODB_LOGFILE_S3KEY = "S3KEY";
//	String DYNAMODB_LOGFILE_FILENAME = "FILENAME";
	
//	String CONFIG_CLIENT = "CLIENTS";
//	String CONFIG_CLIENT_APP = ".APP";
//	String CONFIG_CLIENT_STAGE = ".STAGE";

	Boolean IS_DEBUG = true;

	Integer MAX_ITEM_COUNT = 300;

	String RUBRIC_JSON = "{\"core\" : {   \"data\" : []}}";
	String RUBRIC_JSON_CORE = "core";
	String RUBRIC_JSON_DATA = "data";
	
	String RUBRICS_URL = "http://ekb.zarplata.ru/";
	String RUBRICS_URL_DATA_PREFIX = "var RUBRICS = ";
	String RUBRICS_URL_DATA_POSTFIX = "}];";
	String RUBRICS_URL_DATA_ID = "id"; 
	String RUBRICS_URL_DATA_NAME = "value"; 

	String SUBRUBRICS_URL = "http://ekb.zarplata.ru/api/v1/categories/";
	String SUBRUBRICS_URL_DATA_ERRORS = "errors";
	String SUBRUBRICS_URL_DATA_CATEGORIES = "categories";
	String SUBRUBRICS_URL_DATA_CHILDREN = "children";

	String RESUME_URL = "http://ekb.zarplata.ru/resume?state%5B%5D=1&visibility_type=1&average_salary=1&categories_facets=1&city_id=994&entity=1&geo_id%5B%5D=994&rubric_id%5B%5D=__rubricid__&search_type=fullThrottle&limit=25&offset=";
	Integer RESUME_URL_OFFSET_INIT = 0;
	Integer RESUME_URL_OFFSET_INC = 25;
	String RESUME_URL_DATA_PREFIX_1 = "\"resumes\":[{\"id\"";
	String RESUME_URL_DATA_PREFIX_2 = "var LIST = ";
	String RESUME_URL_DATA_POSTFIX = "}}};";
	String RESUME_URL_DATA_RESUMES = "resumes";
	String RESUME_URL_DATA_ID = "id"; 
	String RESUME_URL_DATA_CONTACT = "contact";
	String RESUME_URL_DATA_NAME = "name"; 
	String RESUME_URL_DATA_HEADER = "header"; 
	String RESUME_URL_DATA_WANTED_SALARY_RUB = "wanted_salary_rub"; 
	
}
