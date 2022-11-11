package com.clinica.api.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinica.api.domain.exception.RecursoNaoEncontradaException;
import com.clinica.api.domain.model.Viagem;
import com.clinica.api.domain.repository.ViagemRepository;
import com.clinica.api.domain.service.ViagemService;

@RestController
@RequestMapping(value = "/viagens")
public class ViagemController {

	@Autowired
	private ViagemRepository viagemRepository;
	
	@Autowired
	private ViagemService viagemService;
	
	@GetMapping
	public List<Viagem> listar() {
		return viagemRepository.findAll();
	}
	
	@GetMapping("/{viagemId}")
	public ResponseEntity<Viagem> buscar(@PathVariable Long viagemId) {
		Optional<Viagem> viagem = viagemRepository.findById(viagemId);
		
		if (viagem.isPresent()) {
			return ResponseEntity.ok(viagem.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Viagem viagem) {
		try {
			viagem = viagemService.salvar(viagem);
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(viagem);
		} catch (RecursoNaoEncontradaException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}
	/**
	 * @param viagemId recebe o código a ser atualizado
	 * @param viagem
	 * @return os dados da viagem atualizada
	 * 
	 * os nomes de edpoint deve ser alterado e padronizado conforme sua necessidade
	 */
	@PutMapping("/{viagemId}")
	public ResponseEntity<?> atualizar(@PathVariable Long viagemId,
			@RequestBody Viagem viagem) {
		try {
			Viagem viagemUsuarioAtual = viagemRepository.findById(viagemId).orElse(null);
			
			if (viagemUsuarioAtual != null) {
				BeanUtils.copyProperties(viagem, viagemUsuarioAtual, "id");
				
				viagemUsuarioAtual = viagemService.salvar(viagemUsuarioAtual);
				return ResponseEntity.ok(viagemUsuarioAtual);
			}
			
			return ResponseEntity.notFound().build();
		
		} catch (RecursoNaoEncontradaException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}
	
}
