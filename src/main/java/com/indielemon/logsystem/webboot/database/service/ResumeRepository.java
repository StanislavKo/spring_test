package com.indielemon.logsystem.webboot.database.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.indielemon.logsystem.webboot.database.entity.Resume;

@Repository
@Transactional
public interface ResumeRepository extends PagingAndSortingRepository<Resume, Long> {

//	Resume save(Resume resume); 
//	@Query("from Resume r where r.name LIKE :likeName ")
//	public Iterable<Resume> findByUserInput(@Param("likeName") String likeName);
	
//	public Iterable<Resume> findByUserInput(String likeName);
	
}
