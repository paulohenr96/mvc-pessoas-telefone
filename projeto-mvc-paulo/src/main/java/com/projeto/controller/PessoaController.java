package com.projeto.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.model.Pessoa;
import com.projeto.model.Telefone;
import com.projeto.repository.PessoaRepository;
import com.projeto.repository.TelefoneRepository;

@Controller
public class PessoaController {

//	
//	@PostMapping(value = "iniciar")
//	public String iniciar() {
//		return "iniciar";
//	}
//	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private TelefoneRepository telefoneRepository;

	@PostMapping("**/salvarpessoa")
	public ModelAndView salvar(Pessoa pessoa) {
		pessoaRepository.save(pessoa);
		return paginaPessoa();

	}

	
	@GetMapping("**/telefones/{idpessoa}")
	public ModelAndView telefones(@PathVariable(name = "idpessoa") Long idpessoa) {
		System.out.println(idpessoa);

		ModelAndView andView = new ModelAndView("telefones.html");
		Pessoa pessoa = pessoaRepository.findById(idpessoa).get();
		return paginaTelefone(pessoa);

	}

	@PostMapping("**/novotelefone/{idpessoa}")
	public ModelAndView novoTelefone(@Valid Telefone telefone, BindingResult result,
			@PathVariable(name = "idpessoa") Long idpessoa) {

		Pessoa pessoa = pessoaRepository.findById(idpessoa).get();
		ModelAndView andView = paginaTelefone(pessoa);

		if (result.hasErrors()) {

			andView.addObject("erros", erros(result));
		} else {
			telefone.setPessoa(pessoa);
			telefoneRepository.save(telefone);
		}


		return andView;

	}

	@GetMapping("**/removertelefone/{idtelefone}")
	public ModelAndView removerTelefone(@PathVariable(name="idtelefone") Long idtelefone) {
		
		Optional<Telefone> optional = telefoneRepository.findById(idtelefone);
		Pessoa p = new Pessoa();
		if (!optional.isEmpty()) {
			p=optional.get().getPessoa();
			telefoneRepository.delete(optional.get());
		}
		return paginaTelefone(p);
	}
	
	@GetMapping("**/telefonespag/{idpessoa}")
	public ModelAndView paginarTelefones (@PageableDefault(size = 2) Pageable pageable,@PathVariable(name="idpessoa") Long idpessoa) {
		
		Optional<Pessoa> optional = pessoaRepository.findById(idpessoa);
		
			Pessoa pessoa = optional.get();
		
			Page<Telefone> telefones = telefoneRepository.findByPessoa(pessoa, pageable);
			
			ModelAndView andView = new ModelAndView ("telefones.html");
			return andView
					.addObject("telefones",telefones)
					.addObject("pessoa",pessoa)	;
			
	}
	
	@GetMapping("**/removerpessoa/{idpessoa}")
	public ModelAndView deletePessoa(@PathVariable(name = "idpessoa") Long idpessoa) {

		pessoaRepository.deleteById(idpessoa);

		return paginaPessoa();
	}

	@GetMapping("**/paginarpessoas")
	public ModelAndView paginarPessoas(@PageableDefault(size = 2,sort = "idade") Pageable pageable) {
		Page<Pessoa> pessoas = pessoaRepository.findAll(pageable);
	
		ModelAndView andView=new ModelAndView("pessoas.html");
		return andView.addObject("pessoas",pessoas);
	}
	
	public ModelAndView paginaPessoa() {

		ModelAndView andView = new ModelAndView("pessoas.html");

		return andView.addObject("pessoas", pessoaRepository.findAll(PageRequest.of(0, 2)));

	}

	public ModelAndView paginaTelefone(Pessoa p) {

		ModelAndView andView = new ModelAndView("telefones.html");

		return andView
				.addObject("pessoa",p)
				.addObject("telefones", telefoneRepository.findByPessoa(p,PageRequest.of(0, 2)));

	}

	public List<String> erros(BindingResult result) {
		List<String> erros = new ArrayList<>();
		List<ObjectError> allErrors = result.getAllErrors();

		for (ObjectError erro : allErrors) {
			String defaultMessage = erro.getDefaultMessage();
			erros.add(defaultMessage);
		}
		
		return erros;

	}
	
	
}
