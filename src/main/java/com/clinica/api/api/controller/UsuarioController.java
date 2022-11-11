package com.clinica.api.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.clinica.api.domain.exception.RecursoEmUsoException;
import com.clinica.api.domain.exception.RecursoNaoEncontradaException;
import com.clinica.api.domain.model.Usuario;
import com.clinica.api.domain.repository.UsuarioRepository;
import com.clinica.api.domain.service.UsuarioService;

@RestController
@RequestMapping(value = "/medicos")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public List<Usuario> listar() {
		return usuarioRepository.findAll();
	}

	@GetMapping("/{usuarioId}")
	public ResponseEntity<Usuario> buscar(@PathVariable Long usuarioId) {
		Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
		// Verifica se estar presente se sim retorna alguma um usuario cadastrado
		if (usuario.isPresent()) {
			return ResponseEntity.ok(usuario.get());
		}

		return ResponseEntity.notFound().build();
	}
	/*
	 * faz uma busca pelo nome exato passado no parametro
	 */

	@GetMapping("/por-nome-exato")
	public List<Usuario> usuariosPorNome(String nome) {
		return usuarioRepository.findTodosUsuariosByNome(nome);
	}
	
	@PostMapping("/salvar")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario adicionar(@RequestBody Usuario usuario) {
		return usuarioService.salvar(usuario);
	}

	@PutMapping("/{usuarioId}")
	public ResponseEntity<Usuario> atualizar(@PathVariable Long usuarioId, @RequestBody Usuario usuario) {
		Optional<Usuario> usuarioAtual = usuarioRepository.findById(usuarioId);

		if (usuarioAtual.isPresent()) {
			BeanUtils.copyProperties(usuario, usuarioAtual.get(), "id");

			Usuario usuarioSalvo = usuarioService.salvar(usuarioAtual.get());
			return ResponseEntity.ok(usuarioSalvo);
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{usuarioId}")
	public ResponseEntity<?> remover(@PathVariable Long usuarioId) {
		try {
			usuarioService.excluir(usuarioId);
			return ResponseEntity.noContent().build();

		} catch (RecursoNaoEncontradaException e) {
			return ResponseEntity.notFound().build();

		} catch (RecursoEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
}
