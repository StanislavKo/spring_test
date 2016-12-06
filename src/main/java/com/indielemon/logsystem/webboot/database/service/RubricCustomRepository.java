package com.indielemon.logsystem.webboot.database.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.indielemon.logsystem.webboot.database.entity.Resume;
import com.indielemon.logsystem.webboot.database.entity.Rubric;

@Repository
@Transactional
public class RubricCustomRepository {

	@PersistenceContext
	private EntityManager em;

	public List<Rubric> findRoot() {
		return em.createQuery("from Rubric r where r.parent IS NULL", Rubric.class).getResultList();
	}

}
