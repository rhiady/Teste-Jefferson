package com.repositories;

import com.domains.Recebimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecebimentoRepository extends JpaRepository<Recebimento, Long> {


    List<Recebimento> findByLancamento_IdLancamento(Long idLancamento);
}
