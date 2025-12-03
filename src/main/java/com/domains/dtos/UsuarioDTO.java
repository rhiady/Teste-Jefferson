package com.domains.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class UsuarioDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Long idUsuario;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 65, message = "Nome deve ter no máximo 65 caracteres")
    private String nome;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Size(max = 100, message = "E-mail deve ter no máximo 100 caracteres")
    private String email;

    @NotNull(message = "Data de cadastro é obrigatória")
    private LocalDate dataCadastro;

    public UsuarioDTO() {}

    public UsuarioDTO(Long idUsuario, String nome, String email, LocalDate dataCadastro) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.dataCadastro = dataCadastro;
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

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "idUsuario=" + idUsuario +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", dataCadastro=" + dataCadastro +
                '}';
    }
}
