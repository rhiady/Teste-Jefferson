package com.domains;

import com.domains.enums.StatusFatura;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    private StatusFatura status;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cartao_id", nullable = false)
    private CartaoCredito cartao;
}
