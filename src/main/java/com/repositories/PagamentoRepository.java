package com.repositories;

import com.domains.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    List<Pagamento> findByLancamento_Id(Long lancamentoId);

    boolean existsByContaOrigem_Id(Long contaId);
}
