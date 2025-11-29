package com.domains;

import com.domains.enums.StatusFatura;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.infra.StatusFaturaConverter;
import com.services.exceptions.RegraNegocioException;
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

    public void validarAtivo() {
        if (Boolean.FALSE.equals(ativo)) {
            throw new RegraNegocioException("Cartão de crédito inativo não pode receber lançamentos.");
        }
    }

    public CartaoCredito() {
    }

    public CartaoCredito(Long idCartaoCredito, String bandeira, String emissor, String apelido, Integer fechamentoFaturaDia, Integer vencimentoFaturaDia, Boolean ativo, StatusFatura statusFatura, Usuario usuario, List<FaturaCartao> faturas) {
        this.idCartaoCredito = idCartaoCredito;
        this.bandeira = bandeira;
        this.emissor = emissor;
        this.apelido = apelido;
        this.fechamentoFaturaDia = fechamentoFaturaDia;
        this.vencimentoFaturaDia = vencimentoFaturaDia;
        this.ativo = ativo;
        this.statusFatura = statusFatura;
        this.usuario = usuario;
        this.faturas = faturas;
    }

    public Long getIdCartaoCredito() {
        return idCartaoCredito;
    }

    public void setIdCartaoCredito(Long idCartaoCredito) {
        this.idCartaoCredito = idCartaoCredito;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getEmissor() {
        return emissor;
    }

    public void setEmissor(String emissor) {
        this.emissor = emissor;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public Integer getFechamentoFaturaDia() {
        return fechamentoFaturaDia;
    }

    public void setFechamentoFaturaDia(Integer fechamentoFaturaDia) {
        this.fechamentoFaturaDia = fechamentoFaturaDia;
    }

    public Integer getVencimentoFaturaDia() {
        return vencimentoFaturaDia;
    }

    public void setVencimentoFaturaDia(Integer vencimentoFaturaDia) {
        this.vencimentoFaturaDia = vencimentoFaturaDia;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public StatusFatura getStatusFatura() {
        return statusFatura;
    }

    public void setStatusFatura(StatusFatura statusFatura) {
        this.statusFatura = statusFatura;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<FaturaCartao> getFaturas() {
        return faturas;
    }

    public void setFaturas(List<FaturaCartao> faturas) {
        this.faturas = faturas;
    }
}
