package com.clinica.api.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.clinica.api.domain.exception.RecursoEmUsoException;
import com.clinica.api.domain.exception.RecursoNaoEncontradaException;
import com.clinica.api.domain.model.Usuario;
import com.clinica.api.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	
	/**
	 * método serve tanto para salvar quanto para atualizar
	 * Utilizando Spring Data
	 */
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	public void excluir(Long usuarioId) {
		try {
			usuarioRepository.deleteById(usuarioId);

		} catch (EmptyResultDataAccessException e) {
			throw new RecursoNaoEncontradaException(
					String.format("Não existe cadastro de usuario com código %d", usuarioId));

		} catch (DataIntegrityViolationException e) {
			throw new RecursoEmUsoException(
					String.format("Usuario de código %d não pode ser removido, pois está em uso", usuarioId));
		}
	}

}
