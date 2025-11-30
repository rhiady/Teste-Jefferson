package com.domains;


import com.domains.enums.MeioPagamento;
import com.domains.enums.StatusLancamento;
import com.domains.enums.TipoLancamento;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.services.exceptions.RegraNegocioException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "lancamento")
@SequenceGenerator(
        name = "seq_lancamento",
        sequenceName = "seq_lancamento",
        allocationSize = 1
)
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_lancamento")
    private Long idLancamento;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String descricao;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal valor;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Convert(converter = com.infra.TipoLancamentoConverter.class)
    @Column(name = "tipo", nullable = false)
    private TipoLancamento tipo;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entidade_id")
    private Entidade entidade;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "centro_custo_id")
    private CentroCusto centroCusto;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_competencia", nullable = false)
    private LocalDate dataCompetencia;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Convert(converter = com.infra.MeioPagamentoConverter.class)
    @Column(name = "meioPagamento", nullable = false)
    private MeioPagamento meioPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id")
    private ContaBancaria contaBancaria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartao_id")
    private CartaoCredito cartaoCredito;

    @Convert(converter = com.infra.StatusLancamentoConverter.class)
    @Column(name = "status", nullable = false)
    private StatusLancamento status ;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Column(name = "valor_baixado", precision = 18, scale = 2, nullable = false)
    private BigDecimal valorBaixado = BigDecimal.ZERO;

    @JsonManagedReference
    @OneToMany(mappedBy = "lancamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pagamento> pagamentos = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "lancamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recebimento> recebimentos = new ArrayList<>();

    public void validarMeioPagamentoCompativel() {
        if (meioPagamento == MeioPagamento.CARTAO && cartaoCredito == null) {
            throw new RegraNegocioException("Meio de pagamento CARTAO exige cartão de crédito vinculado.");
        }
        if ((meioPagamento == MeioPagamento.CONTA
                || meioPagamento == MeioPagamento.PIX
                || meioPagamento == MeioPagamento.DINHEIRO)
                && contaBancaria == null) {
            throw new RegraNegocioException("Meio de pagamento exige conta bancária/espécie definida.");
        }
    }

    public void validarUsuariosRelacionados() {
        if (entidade != null && !entidade.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            throw new RegraNegocioException("Entidade deve pertencer ao mesmo usuário do lançamento.");
        }
        if (centroCusto != null && !centroCusto.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            throw new RegraNegocioException("Centro de custo deve pertencer ao mesmo usuário do lançamento.");
        }
        if (contaBancaria != null && !contaBancaria.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            throw new RegraNegocioException("Conta bancária deve pertencer ao mesmo usuário do lançamento.");
        }
        if (cartaoCredito != null && !cartaoCredito.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            throw new RegraNegocioException("Cartão de crédito deve pertencer ao mesmo usuário do lançamento.");
        }
    }

    public void cancelar() {
        if (this.status == StatusLancamento.BAIXADO) {
            throw new RegraNegocioException("Não é permitido cancelar lançamento totalmente baixado.");
        }
        this.status = StatusLancamento.CANCELADO;
    }

    public Lancamento(StatusLancamento status, BigDecimal valorBaixado) {
        this.status = StatusLancamento.PENDENTE;
        this.valorBaixado = BigDecimal.ZERO;
    }

    public Lancamento() {
    }

    public Lancamento(Long idLancamento, String descricao, BigDecimal valor, Usuario usuario, TipoLancamento tipo, Entidade entidade, CentroCusto centroCusto, LocalDate dataCompetencia, LocalDate dataVencimento, MeioPagamento meioPagamento, ContaBancaria contaBancaria, CartaoCredito cartaoCredito, StatusLancamento status, BigDecimal valorBaixado, List<Pagamento> pagamentos, List<Recebimento> recebimentos) {
        this.idLancamento = idLancamento;
        this.descricao = descricao;
        this.valor = valor;
        this.usuario = usuario;
        this.tipo = tipo;
        this.entidade = entidade;
        this.centroCusto = centroCusto;
        this.dataCompetencia = dataCompetencia;
        this.dataVencimento = dataVencimento;
        this.meioPagamento = meioPagamento;
        this.contaBancaria = contaBancaria;
        this.cartaoCredito = cartaoCredito;
        this.status = status;
        this.valorBaixado = valorBaixado;
        this.pagamentos = pagamentos;
        this.recebimentos = recebimentos;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TipoLancamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

    public CentroCusto getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(CentroCusto centroCusto) {
        this.centroCusto = centroCusto;
    }

    public LocalDate getDataCompetencia() {
        return dataCompetencia;
    }

    public void setDataCompetencia(@NotNull LocalDate dataCompetencia) {
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

    public ContaBancaria getContaBancaria() {
        return contaBancaria;
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
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

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public List<Recebimento> getRecebimentos() {
        return recebimentos;
    }

    public void setRecebimentos(List<Recebimento> recebimentos) {
        this.recebimentos = recebimentos;
    }
}
