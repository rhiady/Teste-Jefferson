package com.domains.dtos;

import com.domains.enums.StatusFatura;
import jakarta.validation.constraints.*;

public class CartaoCreditoDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Long idCartaoCredito;

    @NotBlank(message = "Bandeira é obrigatória")
    @Size(max = 50, message = "Bandeira deve ter no máximo 50 caracteres")
    private String bandeira;

    @NotBlank(message = "Emissor é obrigatório")
    @Size(max = 100, message = "Emissor deve ter no máximo 100 caracteres")
    private String emissor;

    @NotBlank(message = "Apelido é obrigatório")
    @Size(max = 100, message = "Apelido deve ter no máximo 100 caracteres")
    private String apelido;

    @NotNull(message = "Dia de fechamento da fatura é obrigatório")
    @Min(value = 1, message = "Dia de fechamento deve ser entre 1 e 31")
    @Max(value = 31, message = "Dia de fechamento deve ser entre 1 e 31")
    private Integer fechamentoFaturaDia;

    @NotNull(message = "Dia de vencimento da fatura é obrigatório")
    @Min(value = 1, message = "Dia de vencimento deve ser entre 1 e 31")
    @Max(value = 31, message = "Dia de vencimento deve ser entre 1 e 31")
    private Integer vencimentoFaturaDia;

    @NotNull(message = "Status de ativo é obrigatório")
    private Boolean ativo;

    @NotNull(message = "Status da fatura é obrigatório")
    private StatusFatura statusFatura;

    @NotNull(message = "Usuário é obrigatório")
    private Long usuarioId;

    private String usuarioNome;

    public CartaoCreditoDTO() {}

    public CartaoCreditoDTO(Long idCartaoCredito, String bandeira, String emissor, String apelido,
                            Integer fechamentoFaturaDia, Integer vencimentoFaturaDia,
                            Boolean ativo, StatusFatura statusFatura,
                            Long usuarioId, String usuarioNome) {
        this.idCartaoCredito = idCartaoCredito;
        this.bandeira = bandeira;
        this.emissor = emissor;
        this.apelido = apelido;
        this.fechamentoFaturaDia = fechamentoFaturaDia;
        this.vencimentoFaturaDia = vencimentoFaturaDia;
        this.ativo = ativo;
        this.statusFatura = statusFatura;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
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
        return "CartaoCreditoDTO{" +
                "idCartaoCredito=" + idCartaoCredito +
                ", bandeira='" + bandeira + '\'' +
                ", emissor='" + emissor + '\'' +
                ", apelido='" + apelido + '\'' +
                ", fechamentoFaturaDia=" + fechamentoFaturaDia +
                ", vencimentoFaturaDia=" + vencimentoFaturaDia +
                ", ativo=" + ativo +
                ", statusFatura=" + statusFatura +
                ", usuarioId=" + usuarioId +
                ", usuarioNome='" + usuarioNome + '\'' +
                '}';
    }
}
