package com.repositories;

import com.domains.ContaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Long> {

    List<ContaBancaria> findByUsuario_Id(Long usuarioId);

    boolean existsByUsuario_Id(Long usuarioId);
}
