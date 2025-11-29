package com.repositories;

import com.domains.Entidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntidadeRepository extends JpaRepository<Entidade, Long> {

    List<Entidade> findByUsuario_Id(Long usuarioId);

    boolean existsByUsuario_IdAndDocumento(Long usuarioId, String documento);
}
