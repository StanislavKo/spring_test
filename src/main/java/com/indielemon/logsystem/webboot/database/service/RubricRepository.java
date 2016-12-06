package com.indielemon.logsystem.webboot.database.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.indielemon.logsystem.webboot.database.entity.Resume;
import com.indielemon.logsystem.webboot.database.entity.Rubric;

@Repository
@Transactional
public interface RubricRepository extends CrudRepository<Rubric, Long> {

//	Resume save(Resume resume); 
//	@Query("from Rubric r where r.parent IS NULL")
//	public Iterable<Rubric> findRoot();
	
//	public Iterable<Resume> findByUserInput(String likeName);
	
	@Query("from Rubric r where r.e1Id = :e1Id")
	public Rubric findByE1Id(@Param("e1Id") Long e1Id);
}
