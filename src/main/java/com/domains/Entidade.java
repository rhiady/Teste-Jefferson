package com.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "entidade")
@SequenceGenerator(
        name = "seq_entidade",
        sequenceName = "seq_entidade",
        allocationSize = 1
)
public class Entidade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_entidade")
    private Long idEntidade;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String nome;

    @NotBlank
    @Column(nullable = false, length = 30)
    private String documento;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;


    public Entidade() {
    }

    public Entidade(Long idEntidade, String nome, String documento, String tipo, Usuario usuario) {
        this.idEntidade = idEntidade;
        this.nome = nome;
        this.documento = documento;
        this.tipo = tipo;
        this.usuario = usuario;
    }

    public Long getIdEntidade() {
        return idEntidade;
    }

    public void setIdEntidade(Long idEntidade) {
        this.idEntidade = idEntidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
