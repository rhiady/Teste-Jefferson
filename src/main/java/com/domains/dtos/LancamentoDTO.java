package com.domains.dtos;

import com.domains.enums.MeioPagamento;
import com.domains.enums.StatusLancamento;
import com.domains.enums.TipoLancamento;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LancamentoDTO {
    public interface Create {}
    public interface Update {}

    @Null(groups = Create.class, message = "Id deve ser omitido na criação")
    @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
    private Long idLancamento;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @Digits(integer = 15, fraction = 2, message = "Valor inválido")
    private BigDecimal valor;

    @NotNull(message = "Usuário é obrigatório")
    private Long usuarioId;
    private String usuarioNome;

    @NotNull(message = "Tipo de lançamento é obrigatório")
    private TipoLancamento tipo;

    private Long entidadeId;
    private String entidadeNome;

    private Long centroCustoId;
    private String centroCustoNome;

    @NotNull(message = "Data de competência é obrigatória")
    private LocalDate dataCompetencia;

    private LocalDate dataVencimento;

    @NotNull(message = "Meio de pagamento é obrigatório")
    private MeioPagamento meioPagamento;

    private Long contaBancariaId;
    private String contaApelido;

    private Long cartaoCreditoId;
    private String cartaoApelido;

    @NotNull(message = "Status é obrigatório")
    private StatusLancamento status;

    @NotNull(message = "Valor baixado não pode ser nulo")
    @Digits(integer = 15, fraction = 2, message = "Valor baixado inválido")
    private BigDecimal valorBaixado;

    public LancamentoDTO() {}

    public LancamentoDTO(Long idLancamento, String descricao, BigDecimal valor,
                         Long usuarioId, String usuarioNome,
                         TipoLancamento tipo,
                         Long entidadeId, String entidadeNome,
                         Long centroCustoId, String centroCustoNome,
                         LocalDate dataCompetencia, LocalDate dataVencimento,
                         MeioPagamento meioPagamento,
                         Long contaBancariaId, String contaApelido,
                         Long cartaoCreditoId, String cartaoApelido,
                         StatusLancamento status, BigDecimal valorBaixado) {
        this.idLancamento = idLancamento;
        this.descricao = descricao;
        this.valor = valor;
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.tipo = tipo;
        this.entidadeId = entidadeId;
        this.entidadeNome = entidadeNome;
        this.centroCustoId = centroCustoId;
        this.centroCustoNome = centroCustoNome;
        this.dataCompetencia = dataCompetencia;
        this.dataVencimento = dataVencimento;
        this.meioPagamento = meioPagamento;
        this.contaBancariaId = contaBancariaId;
        this.contaApelido = contaApelido;
        this.cartaoCreditoId = cartaoCreditoId;
        this.cartaoApelido = cartaoApelido;
        this.status = status;
        this.valorBaixado = valorBaixado;
    }

    public Long getIdLancamento() {
        return idLancamento;
    }

    public void setIdLancamento(Long idLancamento) {
        this.idLancamento = idLancamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
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

    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public Long getEntidadeId() {
        return entidadeId;
    }

    public void setEntidadeId(Long entidadeId) {
        this.entidadeId = entidadeId;
    }

    public String getEntidadeNome() {
        return entidadeNome;
    }

    public void setEntidadeNome(String entidadeNome) {
        this.entidadeNome = entidadeNome;
    }

    public Long getCentroCustoId() {
        return centroCustoId;
    }

    public void setCentroCustoId(Long centroCustoId) {
        this.centroCustoId = centroCustoId;
    }

    public String getCentroCustoNome() {
        return centroCustoNome;
    }

    public void setCentroCustoNome(String centroCustoNome) {
        this.centroCustoNome = centroCustoNome;
    }

    public LocalDate getDataCompetencia() {
        return dataCompetencia;
    }

    public void setDataCompetencia(LocalDate dataCompetencia) {
        this.dataCompetencia = dataCompetencia;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public MeioPagamento getMeioPagamento() {
        return meioPagamento;
    }

    public void setMeioPagamento(MeioPagamento meioPagamento) {
        this.meioPagamento = meioPagamento;
    }

    public Long getContaBancariaId() {
        return contaBancariaId;
    }

    public void setContaBancariaId(Long contaBancariaId) {
        this.contaBancariaId = contaBancariaId;
    }

    public String getContaApelido() {
        return contaApelido;
    }

    public void setContaApelido(String contaApelido) {
        this.contaApelido = contaApelido;
    }

    public Long getCartaoCreditoId() {
        return cartaoCreditoId;
    }

    public void setCartaoCreditoId(Long cartaoCreditoId) {
        this.cartaoCreditoId = cartaoCreditoId;
    }

    public String getCartaoApelido() {
        return cartaoApelido;
    }

    public void setCartaoApelido(String cartaoApelido) {
        this.cartaoApelido = cartaoApelido;
    }

    public StatusLancamento getStatus() {
        return status;
    }

    public void setStatus(StatusLancamento status) {
        this.status = status;
    }

    public BigDecimal getValorBaixado() {
        return valorBaixado;
    }

    public void setValorBaixado(BigDecimal valorBaixado) {
        this.valorBaixado = valorBaixado;
    }

    @Override
    public String toString() {
        return "LancamentoDTO{" +
                "idLancamento=" + idLancamento +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", usuarioId=" + usuarioId +
                ", usuarioNome='" + usuarioNome + '\'' +
                ", tipo=" + tipo +
                ", entidadeId=" + entidadeId +
                ", entidadeNome='" + entidadeNome + '\'' +
                ", centroCustoId=" + centroCustoId +
                ", centroCustoNome='" + centroCustoNome + '\'' +
                ", dataCompetencia=" + dataCompetencia +
                ", dataVencimento=" + dataVencimento +
                ", meioPagamento=" + meioPagamento +
                ", contaBancariaId=" + contaBancariaId +
                ", contaApelido='" + contaApelido + '\'' +
                ", cartaoCreditoId=" + cartaoCreditoId +
                ", cartaoApelido='" + cartaoApelido + '\'' +
                ", status=" + status +
                ", valorBaixado=" + valorBaixado +
                '}';
    }
}
