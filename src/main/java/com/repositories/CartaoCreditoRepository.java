package com.repositories;

import com.domains.CartaoCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartaoCreditoRepository extends JpaRepository<CartaoCredito, Long> {

    List<CartaoCredito> findByUsuario_Id(Long usuarioId);

    boolean existsByUsuario_Id(Long usuarioId);
}
