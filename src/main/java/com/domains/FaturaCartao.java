package com.domains;

import com.domains.enums.StatusFatura;
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
@Table(name = "fatura_cartao")
@SequenceGenerator(
        name = "seq_fatura_cartao",
        sequenceName = "seq_fatura_cartao",
        allocationSize = 1
)
public class FaturaCartao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_fatura_cartao")
    private Long idFaturaCartao;

    private String competencia;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_fechamento")
    private LocalDate dataFechamento;

    @NotNull
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(name = "valor_total", precision = 18, scale = 2, nullable = false)
    private BigDecimal valorTotal;

    @Convert(converter = com.infra.StatusFaturaConverter.class)
    @Column(name = "status", nullable = false)
    private StatusFatura status;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cartao_id", nullable = false)
    private CartaoCredito cartao;

    public void fechar() {
        if (this.status == StatusFatura.FECHADA) {
            throw new RegraNegocioException("Fatura já está fechada.");
        }
        this.status = StatusFatura.FECHADA;

    }

    public void garantirAbertaParaLancamento() {
        if (this.status == StatusFatura.FECHADA) {
            throw new RegraNegocioException("Não é permitido incluir lançamentos em fatura fechada.");
        }
    }

    public FaturaCartao(BigDecimal valorTotal, StatusFatura status) {
        this.valorTotal = BigDecimal.ZERO;
        this.status = StatusFatura.ABERTA;
    }
    public FaturaCartao() {}

    public FaturaCartao(Long idFaturaCartao, String competencia, LocalDate dataFechamento, LocalDate dataVencimento, BigDecimal valorTotal, StatusFatura status, CartaoCredito cartao) {
        this.idFaturaCartao = idFaturaCartao;
        this.competencia = competencia;
        this.dataFechamento = dataFechamento;
        this.dataVencimento = dataVencimento;
        this.valorTotal = valorTotal;
        this.status = status;
        this.cartao = cartao;
    }

    public Long getIdFaturaCartao() {
        return idFaturaCartao;
    }

    public void setIdFaturaCartao(Long idFaturaCartao) {
        this.idFaturaCartao = idFaturaCartao;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public LocalDate getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDate dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public StatusFatura getStatus() {
        return status;
    }

    public void setStatus(StatusFatura status) {
        this.status = status;
    }

    public CartaoCredito getCartao() {
        return cartao;
    }

    public void setCartao(CartaoCredito cartao) {
        this.cartao = cartao;
    }
}
