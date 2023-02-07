package com.projeto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.repository.PessoaRepository;


@Controller
public class IndexController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	
	@RequestMapping("/")
	public ModelAndView index() {
	ModelAndView andView = new ModelAndView("pessoas.html");
		
	 return andView.addObject("pessoas",pessoaRepository.findAll(PageRequest.of(0, 2)));
	}
}
