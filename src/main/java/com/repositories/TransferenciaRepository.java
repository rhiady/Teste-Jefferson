package com.repositories;

import com.domains.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {


    List<Transferencia> findByUsuario_IdUsuario(Long idUsuario);
}
