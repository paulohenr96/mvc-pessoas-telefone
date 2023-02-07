package com.projeto.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.model.Pessoa;
import com.projeto.model.Telefone;

@Repository
@Transactional
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

	
	List<Telefone> findByPessoa(Pessoa p);
	
	Page<Telefone> findByPessoa(Pessoa p,Pageable page);

}
