package com.domains.dtos;

import com.domains.enums.StatusFatura;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FaturaCartaoDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Long idFaturaCartao;

    @NotBlank(message = "Competência é obrigatória (ex: 03/2025)")
    @Size(max = 10, message = "Competência deve ter no máximo 10 caracteres")
    private String competencia;

    @NotNull(message = "Data de fechamento é obrigatória")
    private LocalDate dataFechamento;

    @NotNull(message = "Data de vencimento é obrigatória")
    private LocalDate dataVencimento;

    @NotNull(message = "Valor total é obrigatório")
    @Digits(integer = 15, fraction = 2, message = "Valor total inválido")
    private BigDecimal valorTotal;

    @NotNull(message = "Status é obrigatório")
    private StatusFatura status;

    @NotNull(message = "Cartão é obrigatório")
    private Long cartaoId;

    private String cartaoApelido;

    public FaturaCartaoDTO() {}

    public FaturaCartaoDTO(Long idFaturaCartao, String competencia, LocalDate dataFechamento,
                           LocalDate dataVencimento, BigDecimal valorTotal,
                           StatusFatura status, Long cartaoId, String cartaoApelido) {
        this.idFaturaCartao = idFaturaCartao;
        this.competencia = competencia;
        this.dataFechamento = dataFechamento;
        this.dataVencimento = dataVencimento;
        this.valorTotal = valorTotal;
        this.status = status;
        this.cartaoId = cartaoId;
        this.cartaoApelido = cartaoApelido;
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

    public Long getCartaoId() {
        return cartaoId;
    }

    public void setCartaoId(Long cartaoId) {
        this.cartaoId = cartaoId;
    }

    public String getCartaoApelido() {
        return cartaoApelido;
    }

    public void setCartaoApelido(String cartaoApelido) {
        this.cartaoApelido = cartaoApelido;
    }

    @Override
    public String toString() {
        return "FaturaCartaoDTO{" +
                "idFaturaCartao=" + idFaturaCartao +
                ", competencia='" + competencia + '\'' +
                ", dataFechamento=" + dataFechamento +
                ", dataVencimento=" + dataVencimento +
                ", valorTotal=" + valorTotal +
                ", status=" + status +
                ", cartaoId=" + cartaoId +
                ", cartaoApelido='" + cartaoApelido + '\'' +
                '}';
    }
}
