package com.indielemon.logsystem.webboot.lifecycle;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.indielemon.logsystem.webboot.consts.Consts;
import com.indielemon.logsystem.webboot.database.entity.Resume;
import com.indielemon.logsystem.webboot.database.entity.Rubric;
import com.indielemon.logsystem.webboot.database.service.ResumeRepository;
import com.indielemon.logsystem.webboot.database.service.RubricRepository;
import com.indielemon.logsystem.webboot.utils.UrlUtils;

@Component
@Profile("hsqldb")
public class InitializerBean implements CommandLineRunner {

	static final Logger logger = Logger.getLogger(InitializerBean.class);

	@Autowired
	private ResumeRepository resumeRepository;

	@Autowired
	private RubricRepository rubricRepository;

	@Override
	public void run(String... arg0) throws Exception {
		try {
			String parentRubricWebPage = UrlUtils.getText(Consts.RUBRICS_URL);
			if (parentRubricWebPage.contains(Consts.RUBRICS_URL_DATA_PREFIX)) {
				logger.info("Consts.RUBRICS_URL_DATA_PREFIX");
				Integer rubricsStartIndex = parentRubricWebPage.indexOf(Consts.RUBRICS_URL_DATA_PREFIX) + Consts.RUBRICS_URL_DATA_PREFIX.length();
				String rubricsList = parentRubricWebPage.substring(rubricsStartIndex,
						parentRubricWebPage.indexOf(Consts.RUBRICS_URL_DATA_POSTFIX, rubricsStartIndex) + 4);
				JSONArray jsonRubrics = new JSONArray(rubricsList);
				logger.info("    jsonRubrics.length(): " + jsonRubrics.length());
				if (jsonRubrics.length() == 0) {
					return;
				}
				for (int i = 0; i < jsonRubrics.length(); i++) {
					try {
						JSONObject jsonRubric = jsonRubrics.getJSONObject(i);
						Long e1Id = jsonRubric.getLong(Consts.RUBRICS_URL_DATA_ID);
						String name = jsonRubric.isNull(Consts.RUBRICS_URL_DATA_NAME) ? null : jsonRubric.getString(Consts.RUBRICS_URL_DATA_NAME);
						logger.info("   rubrics() id: " + e1Id + ", name: " + name);
						if (Consts.IS_DEBUG && e1Id != 91) {
							continue;
						}
						Rubric rubric = rubricRepository.save(new Rubric(e1Id, name));
						saveSubrubrics(rubric, e1Id);
					} catch (JSONException je) {
						je.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveSubrubrics(Rubric parentRubric, Long rubricId) {
		try {
			loadResumes(parentRubric, rubricId);

			String rubricWebPage = UrlUtils.getText(Consts.SUBRUBRICS_URL + rubricId);
			JSONObject jsonWholeRubrics = new JSONObject(rubricWebPage);
			ArrayList<Rubric> children = new ArrayList<Rubric>();
			if (!jsonWholeRubrics.has(Consts.SUBRUBRICS_URL_DATA_ERRORS) && jsonWholeRubrics.has(Consts.SUBRUBRICS_URL_DATA_CATEGORIES)
					&& jsonWholeRubrics.getJSONArray(Consts.SUBRUBRICS_URL_DATA_CATEGORIES).getJSONObject(0).has(Consts.SUBRUBRICS_URL_DATA_CHILDREN)) {
				JSONArray jsonRubrics = jsonWholeRubrics.getJSONArray(Consts.SUBRUBRICS_URL_DATA_CATEGORIES).getJSONObject(0)
						.getJSONArray(Consts.SUBRUBRICS_URL_DATA_CHILDREN);
				logger.info("    saveSubrubrics().    jsonRubrics.length(): " + jsonRubrics.length());
				if (jsonRubrics.length() == 0) {
					return;
				}
				for (int i = 0; i < jsonRubrics.length(); i++) {
					try {
						JSONObject jsonRubric = jsonRubrics.getJSONObject(i);
						Long e1Id = jsonRubric.getLong(Consts.RUBRICS_URL_DATA_ID);
						String name = jsonRubric.isNull(Consts.RUBRICS_URL_DATA_NAME) ? null : jsonRubric.getString(Consts.RUBRICS_URL_DATA_NAME);
						logger.info("    saveSubrubrics()    id: " + e1Id + ", name: " + name);
						Rubric rubric = new Rubric(e1Id, name);
						rubric.setParent(parentRubric);
						children.add(rubric);

						rubric = rubricRepository.save(rubric);
						saveSubrubrics(rubric, e1Id);
					} catch (JSONException je) {
						je.printStackTrace();
					}
				}
				parentRubric.setChildren(children);
				parentRubric = rubricRepository.save(parentRubric);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadResumes(Rubric parentRubric, Long rubricId) {
		try {
			Integer offset = Consts.RESUME_URL_OFFSET_INIT;
			do {
				String url = Consts.RESUME_URL.replaceAll("__rubricid__", "" + rubricId) + offset;
				String resumeWebPage = UrlUtils.getText(url);
				if (resumeWebPage.contains(Consts.RESUME_URL_DATA_PREFIX_1)) {
					Integer resumeStartIndex = resumeWebPage.indexOf(Consts.RESUME_URL_DATA_PREFIX_2) + Consts.RESUME_URL_DATA_PREFIX_2.length();
					String resumeList = resumeWebPage.substring(resumeStartIndex, resumeWebPage.indexOf(Consts.RESUME_URL_DATA_POSTFIX, resumeStartIndex) + 4);
					JSONObject jsonResumeList = new JSONObject(resumeList);
					JSONArray jsonResumes = jsonResumeList.getJSONArray(Consts.RESUME_URL_DATA_RESUMES);
					if (jsonResumes.length() == 0) {
						break;
					}
					for (int i = 0; i < jsonResumes.length(); i++) {
						try {
							JSONObject resume = jsonResumes.getJSONObject(i);
							Long e1Id = resume.getLong(Consts.RESUME_URL_DATA_ID);
							JSONObject contact = resume.getJSONObject(Consts.RESUME_URL_DATA_CONTACT);
							String name = contact.isNull(Consts.RESUME_URL_DATA_NAME) ? null : contact.getString(Consts.RESUME_URL_DATA_NAME);
							String header = resume.isNull(Consts.RESUME_URL_DATA_HEADER) ? null : resume.getString(Consts.RESUME_URL_DATA_HEADER);
							Integer wantedSalaryRub = resume.isNull(Consts.RESUME_URL_DATA_WANTED_SALARY_RUB) ? null
									: resume.getInt(Consts.RESUME_URL_DATA_WANTED_SALARY_RUB);
							logger.debug("id: " + e1Id + ", name: " + name + " [" + name.length() + "], header: " + header + ", wantedSalaryRub: "
									+ wantedSalaryRub);
							resumeRepository.save(new Resume(e1Id, name, header, wantedSalaryRub, parentRubric));
						} catch (JSONException je) {
							je.printStackTrace();
						}
					}
					logger.info("offset: " + offset);
				}
				offset += Consts.RESUME_URL_OFFSET_INC;
				if (Consts.IS_DEBUG && offset >= 25) {
					break;
				}
			} while (true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}