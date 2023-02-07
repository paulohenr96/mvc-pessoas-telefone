package com.projeto.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.model.Pessoa;
@Repository
@Transactional
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	
	
	
	
}
