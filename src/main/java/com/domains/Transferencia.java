package com.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.services.exceptions.RegraNegocioException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transferencia")
@SequenceGenerator(
        name = "seq_transferencia",
        sequenceName = "seq_transferencia",
        allocationSize = 1
)
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_transferencia")
    private Long idTransferencia;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conta_origem_id", nullable = false)
    private ContaBancaria contaOrigem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conta_destino_id", nullable = false)
    private ContaBancaria contaDestino;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(nullable = false)
    private Date data;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(length = 255)
    private String observacao;

    public void validarInvariantes() {
        if (contaOrigem.getIdContaBancaria().equals(contaDestino.getIdContaBancaria())) {
            throw new RegraNegocioException("Conta de origem e destino não podem ser a mesma.");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("Valor da transferência deve ser maior que zero.");
        }
        contaOrigem.validarAtiva();
        contaDestino.validarAtiva();
    }

    public Transferencia() {
    }

    public Transferencia(Long idTransferencia, Usuario usuario, ContaBancaria contaOrigem, ContaBancaria contaDestino, Date data, BigDecimal valor, String observacao) {
        this.idTransferencia = idTransferencia;
        this.usuario = usuario;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.data = data;
        this.valor = valor;
        this.observacao = observacao;
    }
}
