package com.repositories;

import com.domains.Entidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntidadeRepository extends JpaRepository<Entidade, Long> {


    List<Entidade> findByUsuario_IdUsuario(Long idUsuario);

    List<Entidade> findByUsuario_IdUsuarioAndNomeContainingIgnoreCase(Long idUsuario, String nome);
}
