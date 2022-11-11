package com.clinica.api.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinica.api.domain.exception.RecursoNaoEncontradaException;
import com.clinica.api.domain.model.Usuario;
import com.clinica.api.domain.model.Viagem;
import com.clinica.api.domain.repository.UsuarioRepository;
import com.clinica.api.domain.repository.ViagemRepository;

@Service
public class ViagemService {

	@Autowired
	private ViagemRepository viagemRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	/**
	 * 
	 *  método serve tanto para salvar quanto para atualizar
	 * 
	 */
	public Viagem salvar(Viagem viagem) {
		Long usuarioId = viagem.getUsuario().getId();
		
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new RecursoNaoEncontradaException(
						String.format("Não existe cadastrado usuario de código %d", usuarioId)));
		viagem.setUsuario(usuario);
		
		return viagemRepository.save(viagem);
		}

}
