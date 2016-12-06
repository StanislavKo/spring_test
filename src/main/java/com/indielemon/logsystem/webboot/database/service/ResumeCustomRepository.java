package com.indielemon.logsystem.webboot.database.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.indielemon.logsystem.webboot.database.entity.Resume;

@Repository
@Transactional
public class ResumeCustomRepository {

	static final Logger logger = Logger.getLogger(ResumeCustomRepository.class);
	
	@PersistenceContext
	private EntityManager em;

	public List<Resume> findByUserInput(String rubricId, String likeHeader, String likeWantedSalary) {
		StringBuilder query = new StringBuilder("from Resume r where 1 = 1 ");
		query.append(" AND r.rubric.e1Id = " + rubricId);
		if (likeHeader != null) {
			query.append(" AND r.header LIKE '%" + likeHeader + "%'");
		}
		if (likeWantedSalary != null) {
			query.append(" AND r.wantedSalaryRub <= " + likeWantedSalary);
		}
		
		return em.createQuery(query.toString(), Resume.class).setMaxResults(300).getResultList();
	}

}
