package com.domains;

import com.domains.enums.TipoLancamento;
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
@Table(name = "pagamento")
@SequenceGenerator(
        name = "seq_pagamento",
        sequenceName = "seq_pagamento",
        allocationSize = 1
)
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pagamento")
    private Long idPagamento;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lancamento_id", nullable = false)
    private Lancamento lancamento;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_pagamento", nullable = false)
    private LocalDate dataPagamento;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(name = "valor_pago", precision = 18, scale = 2, nullable = false)
    private BigDecimal valorPago;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conta_origem_id", nullable = false)
    private ContaBancaria contaOrigem;

    @Column(length = 255)
    private String observacao;
    public void validarCompatibilidade() {
        if (lancamento.getTipo() != TipoLancamento.PAGAR) {
            throw new RegraNegocioException("Pagamento só é permitido para lançamentos do tipo PAGAR.");
        }
        contaOrigem.validarAtiva();
    }

    public Pagamento() {
    }

    public Pagamento(Long idPagamento, Lancamento lancamento, LocalDate dataPagamento, BigDecimal valorPago, ContaBancaria contaOrigem, String observacao) {
        this.idPagamento = idPagamento;
        this.lancamento = lancamento;
        this.dataPagamento = dataPagamento;
        this.valorPago = valorPago;
        this.contaOrigem = contaOrigem;
        this.observacao = observacao;
    }

    public Long getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Long idPagamento) {
        this.idPagamento = idPagamento;
    }

    public Lancamento getLancamento() {
        return lancamento;
    }

    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(@NotNull LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public ContaBancaria getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(ContaBancaria contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
