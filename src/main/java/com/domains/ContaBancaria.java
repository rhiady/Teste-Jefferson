package com.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    private Boolean ativo;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;


}
