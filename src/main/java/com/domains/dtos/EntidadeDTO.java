package com.domains.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

public class EntidadeDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Long idEntidade;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
    private String nome;

    @NotBlank(message = "Documento é obrigatório")
    @Size(max = 30, message = "Documento deve ter no máximo 30 caracteres")
    private String documento;

    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 50, message = "Tipo deve ter no máximo 50 caracteres")
    private String tipo;

    @NotNull(message = "Usuário é obrigatório")
    private Long usuarioId;

    private String usuarioNome;

    public EntidadeDTO() {}

    public EntidadeDTO(Long idEntidade, String nome, String documento, String tipo,
                       Long usuarioId, String usuarioNome) {
        this.idEntidade = idEntidade;
        this.nome = nome;
        this.documento = documento;
        this.tipo = tipo;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    @Override
    public String toString() {
        return "EntidadeDTO{" +
                "idEntidade=" + idEntidade +
                ", nome='" + nome + '\'' +
                ", documento='" + documento + '\'' +
                ", tipo='" + tipo + '\'' +
                ", usuarioId=" + usuarioId +
                ", usuarioNome='" + usuarioNome + '\'' +
                '}';
    }
}
