package com.domains.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RecebimentoDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Long idRecebimento;

    @NotNull(message = "Lançamento é obrigatório")
    private Long lancamentoId;
    private String lancamentoDescricao;

    @NotNull(message = "Data de recebimento é obrigatória")
    private LocalDate dataRecebimento;

    @NotNull(message = "Valor recebido é obrigatório")
    @Digits(integer = 15, fraction = 2, message = "Valor recebido inválido")
    private BigDecimal valorRecebido;

    @NotNull(message = "Conta de destino é obrigatória")
    private Long contaDestinoId;
    private String contaDestinoApelido;

    @Size(max = 255, message = "Observação deve ter no máximo 255 caracteres")
    private String observacao;

    public RecebimentoDTO() {}

    public RecebimentoDTO(Long idRecebimento, Long lancamentoId, String lancamentoDescricao,
                          LocalDate dataRecebimento, BigDecimal valorRecebido,
                          Long contaDestinoId, String contaDestinoApelido,
                          String observacao) {
        this.idRecebimento = idRecebimento;
        this.lancamentoId = lancamentoId;
        this.lancamentoDescricao = lancamentoDescricao;
        this.dataRecebimento = dataRecebimento;
        this.valorRecebido = valorRecebido;
        this.contaDestinoId = contaDestinoId;
        this.contaDestinoApelido = contaDestinoApelido;
        this.observacao = observacao;
    }

    public Long getIdRecebimento() {
        return idRecebimento;
    }

    public void setIdRecebimento(Long idRecebimento) {
        this.idRecebimento = idRecebimento;
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

    public LocalDate getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(LocalDate dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public BigDecimal getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(BigDecimal valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    public Long getContaDestinoId() {
        return contaDestinoId;
    }

    public void setContaDestinoId(Long contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }

    public String getContaDestinoApelido() {
        return contaDestinoApelido;
    }

    public void setContaDestinoApelido(String contaDestinoApelido) {
        this.contaDestinoApelido = contaDestinoApelido;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return "RecebimentoDTO{" +
                "idRecebimento=" + idRecebimento +
                ", lancamentoId=" + lancamentoId +
                ", lancamentoDescricao='" + lancamentoDescricao + '\'' +
                ", dataRecebimento=" + dataRecebimento +
                ", valorRecebido=" + valorRecebido +
                ", contaDestinoId=" + contaDestinoId +
                ", contaDestinoApelido='" + contaDestinoApelido + '\'' +
                ", observacao='" + observacao + '\'' +
                '}';
    }
}
