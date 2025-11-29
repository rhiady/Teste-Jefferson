package com.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.services.exceptions.RegraNegocioException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "movimento_conta")
@SequenceGenerator(
        name = "seq_movimento_conta",
        sequenceName = "seq_movimento_conta",
        allocationSize = 1
)
public class MovimentoConta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_movimento_conta")
    private Long idMovimentoConta;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conta_id", nullable = false)
    private ContaBancaria conta;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy ")
    @Column(name = "data_movimento", nullable = false)
    private LocalDate dataMovimento;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(length = 255)
    private String historico;

    @Column(name = "referencia_id")
    private Long referenciaId;

    @Column(name = "referencia_tipo", length = 50)
    private String referenciaTipo;

    public void validar() {
        conta.validarAtiva();
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("Valor do movimento deve ser positivo.");
        }
    }

    public MovimentoConta() {
    }

    public MovimentoConta(Long idMovimentoConta, ContaBancaria conta, LocalDate dataMovimento, BigDecimal valor, String historico, Long referenciaId, String referenciaTipo) {
        this.idMovimentoConta = idMovimentoConta;
        this.conta = conta;
        this.dataMovimento = dataMovimento;
        this.valor = valor;
        this.historico = historico;
        this.referenciaId = referenciaId;
        this.referenciaTipo = referenciaTipo;
    }

    public Long getIdMovimentoConta() {
        return idMovimentoConta;
    }

    public void setIdMovimentoConta(Long idMovimentoConta) {
        this.idMovimentoConta = idMovimentoConta;
    }

    public ContaBancaria getConta() {
        return conta;
    }

    public void setConta(ContaBancaria conta) {
        this.conta = conta;
    }

    public LocalDate getDataMovimento() {
        return dataMovimento;
    }

    public void setDataMovimento(LocalDate dataMovimento) {
        this.dataMovimento = dataMovimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public Long getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(Long referenciaId) {
        this.referenciaId = referenciaId;
    }

    public String getReferenciaTipo() {
        return referenciaTipo;
    }

    public void setReferenciaTipo(String referenciaTipo) {
        this.referenciaTipo = referenciaTipo;
    }
}
