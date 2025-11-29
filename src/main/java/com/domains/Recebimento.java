package com.domains;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "recebimento")
@SequenceGenerator(
        name = "seq_recebimento",
        sequenceName = "seq_recebimento",
        allocationSize = 1
)
public class Recebimento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_recebimento")
    private Integer idRecebimento;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lancamento_id", nullable = false)
    private Lancamento lancamento;

    @NotNull
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_recebimento", nullable = false)
    private Date dataRecebimento;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(name = "valor_recebido", precision = 18, scale = 2, nullable = false)
    private BigDecimal valorRecebido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conta_destino_id", nullable = false)
    private ContaBancaria contaDestino;

    @Column(length = 255)
    private String observacao;

}
