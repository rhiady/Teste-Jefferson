package com.domains;

import com.domains.enums.StatusFatura;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.infra.StatusFaturaConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cartao_credito")
@SequenceGenerator(
        name = "seq_cartao_credito",
        sequenceName = "seq_cartao_credito",
        allocationSize = 1
)

public class CartaoCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cartao_credito")
    private Long idCartaoCredito;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String bandeira;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String emissor;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String apelido;

    @NotNull
    @Column(name = "fechamento_fatura_dia", nullable = false)
    private Integer fechamentoFaturaDia;

    @NotNull
    @Column(name = "vencimento_fatura_dia", nullable = false)
    private Integer vencimentoFaturaDia;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Convert(converter = StatusFaturaConverter.class)
    @Column(name = "status_fatura", nullable = false)
    private StatusFatura statusFatura;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @JsonManagedReference
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FaturaCartao> faturas = new ArrayList<>();




}
