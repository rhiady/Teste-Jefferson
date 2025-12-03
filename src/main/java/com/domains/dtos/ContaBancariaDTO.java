package com.domains.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContaBancariaDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Long idContaBancaria;

    @NotBlank(message = "Instituição é obrigatória")
    @Size(max = 150, message = "Instituição deve ter no máximo 150 caracteres")
    private String instituicao;

    @NotBlank(message = "Agência é obrigatória")
    @Size(max = 20, message = "Agência deve ter no máximo 20 caracteres")
    private String agencia;

    @NotBlank(message = "Número da conta é obrigatório")
    @Size(max = 20, message = "Número deve ter no máximo 20 caracteres")
    private String numero;

    @NotBlank(message = "Apelido é obrigatório")
    @Size(max = 20, message = "Apelido deve ter no máximo 20 caracteres")
    private String apelido;

    @NotNull(message = "Saldo inicial é obrigatório")
    @Digits(integer = 15, fraction = 2, message = "Saldo inicial inválido")
    private BigDecimal saldoInicial;

    @NotNull(message = "Data do saldo inicial é obrigatória")
    private LocalDate dataSaldoInicial;

    @NotNull(message = "Status de ativo é obrigatório")
    private Boolean ativo;

    @NotNull(message = "Usuário é obrigatório")
    private Long usuarioId;

    private String usuarioNome;

    public ContaBancariaDTO() {}

    public ContaBancariaDTO(Long idContaBancaria, String instituicao, String agencia, String numero,
                            String apelido, BigDecimal saldoInicial, LocalDate dataSaldoInicial,
                            Boolean ativo, Long usuarioId, String usuarioNome) {
        this.idContaBancaria = idContaBancaria;
        this.instituicao = instituicao;
        this.agencia = agencia;
        this.numero = numero;
        this.apelido = apelido;
        this.saldoInicial = saldoInicial;
        this.dataSaldoInicial = dataSaldoInicial;
        this.ativo = ativo;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
    }

    public Long getIdContaBancaria() {
        return idContaBancaria;
    }

    public void setIdContaBancaria(Long idContaBancaria) {
        this.idContaBancaria = idContaBancaria;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public LocalDate getDataSaldoInicial() {
        return dataSaldoInicial;
    }

    public void setDataSaldoInicial(LocalDate dataSaldoInicial) {
        this.dataSaldoInicial = dataSaldoInicial;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    @Override
    public String toString() {
        return "ContaBancariaDTO{" +
                "idContaBancaria=" + idContaBancaria +
                ", instituicao='" + instituicao + '\'' +
                ", agencia='" + agencia + '\'' +
                ", numero='" + numero + '\'' +
                ", apelido='" + apelido + '\'' +
                ", saldoInicial=" + saldoInicial +
                ", dataSaldoInicial=" + dataSaldoInicial +
                ", ativo=" + ativo +
                ", usuarioId=" + usuarioId +
                ", usuarioNome='" + usuarioNome + '\'' +
                '}';
    }
}
