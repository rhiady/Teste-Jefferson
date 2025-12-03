package com.domains.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PagamentoDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Long idPagamento;

    @NotNull(message = "Lançamento é obrigatório")
    private Long lancamentoId;
    private String lancamentoDescricao;

    @NotNull(message = "Data de pagamento é obrigatória")
    private LocalDate dataPagamento;

    @NotNull(message = "Valor pago é obrigatório")
    @Digits(integer = 15, fraction = 2, message = "Valor pago inválido")
    private BigDecimal valorPago;

    @NotNull(message = "Conta de origem é obrigatória")
    private Long contaOrigemId;
    private String contaOrigemApelido;

    @Size(max = 255, message = "Observação deve ter no máximo 255 caracteres")
    private String observacao;

    public PagamentoDTO() {}

    public PagamentoDTO(Long idPagamento, Long lancamentoId, String lancamentoDescricao,
                        LocalDate dataPagamento, BigDecimal valorPago,
                        Long contaOrigemId, String contaOrigemApelido,
                        String observacao) {
        this.idPagamento = idPagamento;
        this.lancamentoId = lancamentoId;
        this.lancamentoDescricao = lancamentoDescricao;
        this.dataPagamento = dataPagamento;
        this.valorPago = valorPago;
        this.contaOrigemId = contaOrigemId;
        this.contaOrigemApelido = contaOrigemApelido;
        this.observacao = observacao;
    }

    public Long getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Long idPagamento) {
        this.idPagamento = idPagamento;
    }

    public Long getLancamentoId() {
        return lancamentoId;
    }

    public void setLancamentoId(Long lancamentoId) {
        this.lancamentoId = lancamentoId;
    }

    public String getLancamentoDescricao() {
        return lancamentoDescricao;
    }

    public void setLancamentoDescricao(String lancamentoDescricao) {
        this.lancamentoDescricao = lancamentoDescricao;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public Long getContaOrigemId() {
        return contaOrigemId;
    }

    public void setContaOrigemId(Long contaOrigemId) {
        this.contaOrigemId = contaOrigemId;
    }

    public String getContaOrigemApelido() {
        return contaOrigemApelido;
    }

    public void setContaOrigemApelido(String contaOrigemApelido) {
        this.contaOrigemApelido = contaOrigemApelido;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return "PagamentoDTO{" +
                "idPagamento=" + idPagamento +
                ", lancamentoId=" + lancamentoId +
                ", lancamentoDescricao='" + lancamentoDescricao + '\'' +
                ", dataPagamento=" + dataPagamento +
                ", valorPago=" + valorPago +
                ", contaOrigemId=" + contaOrigemId +
                ", contaOrigemApelido='" + contaOrigemApelido + '\'' +
                ", observacao='" + observacao + '\'' +
                '}';
    }
}
