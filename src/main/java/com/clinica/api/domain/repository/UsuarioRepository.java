package com.clinica.api.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinica.api.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	/*
	 * Utilizando Keyword do Spring boot retorna um nome exato O Spring aceita que
	 * adicione o nome entre o find e o By Exemplos na documentação:
	 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference
	 * Table 3. Supported keywords inside method names
	 */
	List<Usuario> findTodosUsuariosByNome(String nome);

	/*
	 * Nome é o atribudo da classe model o Spring indentifica automaticamente Busca
	 * uma lista onde conter uma derterminada letra passada no parametro da viagem
	 *
	 */
	List<Usuario> findTodosUsuariosByNomeContaining(String nome);
	
	
	
}
