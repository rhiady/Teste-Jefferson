package com.repositories;

import com.domains.CentroCusto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CentroCustoRepository extends JpaRepository<CentroCusto, Long> {

    List<CentroCusto> findByUsuario_Id(Long usuarioId);

    boolean existsByUsuario_IdAndCodigo(Long usuarioId, String codigo);
}
