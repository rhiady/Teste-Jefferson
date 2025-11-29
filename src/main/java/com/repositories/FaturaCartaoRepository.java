package com.repositories;

import com.domains.FaturaCartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FaturaCartaoRepository extends JpaRepository<FaturaCartao, Long> {

    Optional<FaturaCartao> findByCartao_IdAndCompetencia(Long cartaoId, LocalDate competencia);

    List<FaturaCartao> findByCartao_Id(Long cartaoId);
}

