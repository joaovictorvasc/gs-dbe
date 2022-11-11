package com.clinica.api.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.clinica.api.domain.model.Viagem;
import com.clinica.api.domain.model.Usuario;


/***
 * 
 * @author Renato
 * Exemplos de utilização de consultas com Spring Data
 * você deve modificar os nomes de métodos conforme padrões e nescessidades.
 *
 */
@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Long>{

	/**
	 * Utilizando Keyword do Spring boot
	 */

	/**
	 * 
	 * @param nome busca nome do usuario relacionado a viagem
	 * @param usuarioId relacionado a viagem
	 * @return uma lista de usuarios
	 * 
	 */
	List<Viagem> findByDestinoContainingAndMedicoId(String destino, Long usuarioId);
	
	/**
	 * Customizando viagens com @Query e JPQL 
	 * 
	 * @param destino da viagem
	 * @param usuarioId é o id do usuario relacionado
	 * @return uma lista de viagens relacionada ao id do ususario cadastrado
	 */
	@Query("from Viagem where destino like %:destino% and usuario.id = :id")
	List<Viagem> destinoViagemIdUsuario (String destino, @Param("id") Long usuarioId);
	
	
	/**
	 * Customizando viagens utilizando Arquivo de mapeamento orm.xml 
	 * 
	 * @param destino da viagem
	 * @param usuarioId é o id do usuario relacionado a uma viagem 
	 * @return retorna uma lista de viagens relacionada ao id do usuario cadastrado
	 */
	List<Viagem> destinoConsultaIdUsuarioRelacionado (String destino, @Param("id") Long destinoId);
	
	
	
}
