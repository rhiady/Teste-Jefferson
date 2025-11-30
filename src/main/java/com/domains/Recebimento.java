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
@Table(name = "recebimento")
@SequenceGenerator(
        name = "seq_recebimento",
        sequenceName = "seq_recebimento",
        allocationSize = 1
)
public class Recebimento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_recebimento")
    private Long idRecebimento;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lancamento_id", nullable = false)
    private Lancamento lancamento;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_recebimento", nullable = false)
    private LocalDate dataRecebimento;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(name = "valor_recebido", precision = 18, scale = 2, nullable = false)
    private BigDecimal valorRecebido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conta_destino_id", nullable = false)
    private ContaBancaria contaDestino;

    @Column(length = 255)
    private String observacao;

    public void validarCompatibilidade() {
        if (lancamento.getTipo() != TipoLancamento.RECEBER) {
            throw new RegraNegocioException("Recebimento só é permitido para lançamentos do tipo RECEBER.");
        }
        contaDestino.validarAtiva();
    }

    public Recebimento() {
    }

    public Recebimento(Long idRecebimento, Lancamento lancamento, LocalDate dataRecebimento, BigDecimal valorRecebido, ContaBancaria contaDestino, String observacao) {
        this.idRecebimento = idRecebimento;
        this.lancamento = lancamento;
        this.dataRecebimento = dataRecebimento;
        this.valorRecebido = valorRecebido;
        this.contaDestino = contaDestino;
        this.observacao = observacao;
    }

    public Long getIdRecebimento() {
        return idRecebimento;
    }

    public void setIdRecebimento(Long idRecebimento) {
        this.idRecebimento = idRecebimento;
    }

    public Lancamento getLancamento() {
        return lancamento;
    }

    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
    }

    public LocalDate getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(@NotNull LocalDate dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public BigDecimal getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(BigDecimal valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    public ContaBancaria getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(ContaBancaria contaDestino) {
        this.contaDestino = contaDestino;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

}
