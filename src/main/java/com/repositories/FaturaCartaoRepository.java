package com.repositories;

import com.domains.FaturaCartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FaturaCartaoRepository extends JpaRepository<FaturaCartao, Long> {


    List<FaturaCartao> findByCartao_IdCartaoCredito(Long idCartaoCredito);

    Optional<FaturaCartao> findByCartao_IdCartaoCreditoAndCompetencia(Long idCartaoCredito, String competencia);
}

