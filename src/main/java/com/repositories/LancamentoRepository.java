package com.repositories;

import com.domains.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    Page<Lancamento> findByUsuario_Id(Long usuarioId, Pageable pageable);

    Page<Lancamento> findByContaBancaria_Id(Long contaId, Pageable pageable);

    Page<Lancamento> findByCartaoCredito_Id(Long cartaoId, Pageable pageable);

    Page<Lancamento> findByEntidade_Id(Long entidadeId, Pageable pageable);

    Page<Lancamento> findByCentroCusto_Id(Long centroCustoId, Pageable pageable);

    boolean existsByUsuario_Id(Long usuarioId);
}
