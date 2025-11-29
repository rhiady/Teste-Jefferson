package com.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.services.exceptions.RegraNegocioException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "conta_bancaria")
@SequenceGenerator(
        name = "seq_conta_bancaria",
        sequenceName = "seq_conta_bancaria",
        allocationSize = 1
)
public class ContaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_conta_bancaria")
    private Long idContaBancaria;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String instituicao;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String agencia;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String numero;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String apelido;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(name = "saldo_inicial", precision = 18, scale = 2, nullable = false)
    private BigDecimal saldoInicial;

    @NotNull
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_saldo_inicial", nullable = false)
    private LocalDate dataSaldoInicial;

    @NotNull
    @Column(name = "ativa", nullable = false)
    private Boolean ativa;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public void validarAtiva() {
        if (Boolean.FALSE.equals(ativa)) {
            throw new RegraNegocioException("Conta bancária inativa não pode ser movimentada.");
        }
    }

    public ContaBancaria(Boolean ativa) {
        this.ativa = Boolean.TRUE;
    }

    public ContaBancaria() {
    }

    public ContaBancaria(Long idContaBancaria, String instituicao, String agencia, String numero, String apelido, BigDecimal saldoInicial, LocalDate dataSaldoInicial, Boolean ativa, Usuario usuario) {
        this.idContaBancaria = idContaBancaria;
        this.instituicao = instituicao;
        this.agencia = agencia;
        this.numero = numero;
        this.apelido = apelido;
        this.saldoInicial = saldoInicial;
        this.dataSaldoInicial = dataSaldoInicial;
        this.ativa = ativa;
        this.usuario = usuario;
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

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


}
