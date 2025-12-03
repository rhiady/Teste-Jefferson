package com.domains.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MovimentoContaDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Long idMovimentoConta;

    @NotNull(message = "Conta é obrigatória")
    private Long contaId;
    private String contaApelido;

    @NotNull(message = "Data do movimento é obrigatória")
    private LocalDate dataMovimento;

    @NotNull(message = "Valor é obrigatório")
    @Digits(integer = 15, fraction = 2, message = "Valor inválido")
    private BigDecimal valor;

    @Size(max = 255, message = "Histórico deve ter no máximo 255 caracteres")
    private String historico;

    private Long referenciaId;

    @Size(max = 50, message = "Referência tipo deve ter no máximo 50 caracteres")
    private String referenciaTipo;

    public MovimentoContaDTO() {}

    public MovimentoContaDTO(Long idMovimentoConta, Long contaId, String contaApelido,
                             LocalDate dataMovimento, BigDecimal valor,
                             String historico, Long referenciaId, String referenciaTipo) {
        this.idMovimentoConta = idMovimentoConta;
        this.contaId = contaId;
        this.contaApelido = contaApelido;
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

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long contaId) {
        this.contaId = contaId;
    }

    public String getContaApelido() {
        return contaApelido;
    }

    public void setContaApelido(String contaApelido) {
        this.contaApelido = contaApelido;
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

    @Override
    public String toString() {
        return "MovimentoContaDTO{" +
                "idMovimentoConta=" + idMovimentoConta +
                ", contaId=" + contaId +
                ", contaApelido='" + contaApelido + '\'' +
                ", dataMovimento=" + dataMovimento +
                ", valor=" + valor +
                ", historico='" + historico + '\'' +
                ", referenciaId=" + referenciaId +
                ", referenciaTipo='" + referenciaTipo + '\'' +
                '}';
    }
}
