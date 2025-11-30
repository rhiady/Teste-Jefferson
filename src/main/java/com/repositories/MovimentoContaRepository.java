package com.repositories;

import com.domains.MovimentoConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimentoContaRepository extends JpaRepository<MovimentoConta, Long> {


    List<MovimentoConta> findByConta_IdContaBancaria(Long idContaBancaria);

    List<MovimentoConta> findByConta_IdContaBancariaAndDataMovimentoBetween(
            Long idContaBancaria, LocalDate inicio, LocalDate fim);
}
