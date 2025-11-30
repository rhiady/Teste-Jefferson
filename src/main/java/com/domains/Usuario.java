package com.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "usuario")
@SequenceGenerator(
        name = "seq_usuario",
        sequenceName = "seq_usuario",
        allocationSize = 1
)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    private Long idUsuario;

    @NotBlank
    @Column(nullable = false, length = 65)
    private String nome;

    @NotBlank
    @Column(nullable = false, length = 20, unique = true)
    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate criadoEm ;

    @JsonManagedReference
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<ContaBancaria> contas = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<CartaoCredito> cartoes = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Entidade> entidades = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<CentroCusto> centros = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Lancamento> lancamentos = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Transferencia> transferencias = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(Long idUsuario, String nome, String email, LocalDate criadoEm, List<ContaBancaria> contas, List<CartaoCredito> cartoes, List<Entidade> entidades, List<CentroCusto> centros, List<Lancamento> lancamentos, List<Transferencia> transferencias) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.criadoEm = criadoEm;
        this.contas = contas;
        this.cartoes = cartoes;
        this.entidades = entidades;
        this.centros = centros;
        this.lancamentos = lancamentos;
        this.transferencias = transferencias;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDate criadoEm) {
        this.criadoEm = criadoEm;
    }

    public List<ContaBancaria> getContas() {
        return contas;
    }

    public void setContas(List<ContaBancaria> contas) {
        this.contas = contas;
    }

    public List<CartaoCredito> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<CartaoCredito> cartoes) {
        this.cartoes = cartoes;
    }

    public List<Entidade> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<Entidade> entidades) {
        this.entidades = entidades;
    }

    public List<CentroCusto> getCentros() {
        return centros;
    }

    public void setCentros(List<CentroCusto> centros) {
        this.centros = centros;
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    public List<Transferencia> getTransferencias() {
        return transferencias;
    }

    public void setTransferencias(List<Transferencia> transferencias) {
        this.transferencias = transferencias;
    }
}
