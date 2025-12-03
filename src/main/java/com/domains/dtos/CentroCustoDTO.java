package com.domains.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

public class CentroCustoDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Long idCentroCusto;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 120, message = "Nome deve ter no máximo 120 caracteres")
    private String nome;

    @NotBlank(message = "Código é obrigatório")
    @Size(max = 30, message = "Código deve ter no máximo 30 caracteres")
    private String codigo;

    @NotNull(message = "Status de ativo é obrigatório")
    private Boolean ativo;

    @NotNull(message = "Usuário é obrigatório")
    private Long usuarioId;

    private String usuarioNome;

    public CentroCustoDTO() {}

    public CentroCustoDTO(Long idCentroCusto, String nome, String codigo,
                          Boolean ativo, Long usuarioId, String usuarioNome) {
        this.idCentroCusto = idCentroCusto;
        this.nome = nome;
        this.codigo = codigo;
        this.ativo = ativo;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
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
        return "CentroCustoDTO{" +
                "idCentroCusto=" + idCentroCusto +
                ", nome='" + nome + '\'' +
                ", codigo='" + codigo + '\'' +
                ", ativo=" + ativo +
                ", usuarioId=" + usuarioId +
                ", usuarioNome='" + usuarioNome + '\'' +
                '}';
    }
}
