package com.repositories;

import com.domains.Lancamento;
import com.domains.enums.StatusLancamento;
import com.domains.enums.TipoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {


    List<Lancamento> findByUsuario_IdUsuario(Long idUsuario);

    List<Lancamento> findByUsuario_IdUsuarioAndTipo(Long idUsuario, TipoLancamento tipo);

    List<Lancamento> findByUsuario_IdUsuarioAndStatus(Long idUsuario, StatusLancamento status);

    List<Lancamento> findByUsuario_IdUsuarioAndDataCompetenciaBetween(Long idUsuario, LocalDate inicio, LocalDate fim);
}
