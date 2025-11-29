package com.repositories;

import com.domains.MovimentoConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentoContaRepository extends JpaRepository<MovimentoConta, Long> {

    List<MovimentoConta> findByConta_Id(Long contaId);

    boolean existsByConta_Id(Long contaId);
}
