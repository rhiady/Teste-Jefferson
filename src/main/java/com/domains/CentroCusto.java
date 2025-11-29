package com.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

@Entity
@Table(name = "centro_custo")
@SequenceGenerator(
        name = "seq_centro_custo",
        sequenceName = "seq_centro_custo",
        allocationSize = 1
)
public class CentroCusto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_centro_custo")
    private Long idCentroCusto;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nome;

    @NotBlank
    @Column(name = "codigo", nullable = false, length = 30)
    private String codigo;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = Boolean.TRUE;

    @JsonManagedReference
    @OneToMany(mappedBy = "centroCusto", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Lancamento> lancamentos = new ArrayList<>();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

}
