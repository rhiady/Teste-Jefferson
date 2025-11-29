package com.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.services.exceptions.RegraNegocioException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

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

    public CentroCusto(Boolean ativo) {
        this.ativo = Boolean.TRUE;
    }
    public void inativar() {
        this.ativo = Boolean.FALSE;
    }

    public void validarAtivo() {
        if (Boolean.FALSE.equals(ativo)) {
            throw new RegraNegocioException("Centro de custo inativo não pode ser utilizado em lançamentos.");
        }
    }

    public CentroCusto() {
    }

    public CentroCusto(Long idCentroCusto, String nome, String codigo, Boolean ativo, List<Lancamento> lancamentos, Usuario usuario) {
        this.idCentroCusto = idCentroCusto;
        this.nome = nome;
        this.codigo = codigo;
        this.ativo = ativo;
        this.lancamentos = lancamentos;
        this.usuario = usuario;
    }

    public Long getIdCentroCusto() {
        return idCentroCusto;
    }

    public void setIdCentroCusto(Long idCentroCusto) {
        this.idCentroCusto = idCentroCusto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
