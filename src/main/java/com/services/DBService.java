package com.services;

import com.domains.*;
import com.domains.enums.*;
import com.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DBService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private ContaBancariaRepository contaRepo;

    @Autowired
    private CartaoCreditoRepository cartaoRepo;

    @Autowired
    private FaturaCartaoRepository faturaRepo;

    @Autowired
    private EntidadeRepository entidadeRepo;

    @Autowired
    private CentroCustoRepository centroRepo;

    @Autowired
    private LancamentoRepository lancamentoRepo;

    @Autowired
    private PagamentoRepository pagamentoRepo;

    @Autowired
    private RecebimentoRepository recebimentoRepo;

    @Autowired
    private MovimentoContaRepository movimentoRepo;

    @Autowired
    private TransferenciaRepository transferenciaRepo;

    @Transactional
    public void initDB() {

        LocalDate hoje = LocalDate.now();

        Usuario usuario01 = new Usuario(
                null,
                "Usuário Demo",
                "demo@financas.com",
                hoje,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        usuarioRepo.save(usuario01);

        ContaBancaria conta01 = new ContaBancaria(
                null,
                "Nubank",
                "0001",
                "12345-6",
                "Conta Principal",
                new BigDecimal("1500.00"),
                hoje,
                true,
                usuario01
        );

        ContaBancaria conta02 = new ContaBancaria(
                null,
                "Caixa Econômica",
                "1234",
                "98765-4",
                "Poupança",
                new BigDecimal("2000.00"),
                hoje,
                true,
                usuario01
        );

        contaRepo.saveAll(List.of(conta01, conta02));

        CartaoCredito cartao01 = new CartaoCredito(
                null,
                "Mastercard",
                "Nubank",
                "Nubank Roxo",
                5,
                15,
                true,
                StatusFatura.ABERTA,
                usuario01,
                new ArrayList<>()
        );

        cartaoRepo.save(cartao01);

        FaturaCartao fatura01 = new FaturaCartao(
                null,
                "03/2025",
                hoje,
                hoje.plusDays(15),
                BigDecimal.ZERO,
                StatusFatura.ABERTA,
                cartao01
        );

        faturaRepo.save(fatura01);

        Entidade entidade01 = new Entidade(
                null,
                "Companhia de Energia",
                "00987654000133",
                "EMPRESA",
                usuario01
        );

        Entidade entidade02 = new Entidade(
                null,
                "Supermercado Central",
                "12345678000199",
                "EMPRESA",
                usuario01
        );

        Entidade entidade03 = new Entidade(
                null,
                "Empresa XPTO Ltda",
                "11222333000188",
                "EMPRESA",
                usuario01
        );

        entidadeRepo.saveAll(List.of(entidade01, entidade02, entidade03));

        CentroCusto centro01 = new CentroCusto(
                null,
                "Casa",
                "CASA",
                true,
                new ArrayList<>(),
                usuario01
        );

        CentroCusto centro02 = new CentroCusto(
                null,
                "Trabalho",
                "TRAB",
                true,
                new ArrayList<>(),
                usuario01
        );

        centroRepo.saveAll(List.of(centro01, centro02));

        Lancamento lancamento01 = new Lancamento(
                null,
                "Conta de energia elétrica",
                new BigDecimal("230.50"),
                usuario01,
                TipoLancamento.PAGAR,
                entidade01,
                centro01,
                hoje,
                hoje.plusDays(7),
                MeioPagamento.CONTA,
                conta01,
                null,
                StatusLancamento.PENDENTE,
                BigDecimal.ZERO,
                new ArrayList<>(),
                new ArrayList<>()
        );

        Lancamento lancamento02 = new Lancamento(
                null,
                "Compras no supermercado",
                new BigDecimal("180.75"),
                usuario01,
                TipoLancamento.PAGAR,
                entidade02,
                centro01,
                hoje,
                hoje.plusDays(30),
                MeioPagamento.CARTAO,
                null,
                cartao01,
                StatusLancamento.PENDENTE,
                BigDecimal.ZERO,
                new ArrayList<>(),
                new ArrayList<>()
        );

        Lancamento lancamento03 = new Lancamento(
                null,
                "Salário mensal",
                new BigDecimal("5000.00"),
                usuario01,
                TipoLancamento.RECEBER,
                entidade03,
                centro02,
                hoje,
                hoje,
                MeioPagamento.CONTA,
                conta01,
                null,
                StatusLancamento.PENDENTE,
                BigDecimal.ZERO,
                new ArrayList<>(),
                new ArrayList<>()
        );

        lancamentoRepo.saveAll(List.of(lancamento01, lancamento02, lancamento03));

        Pagamento pagamento01 = new Pagamento(
                null,
                lancamento01,
                hoje,
                new BigDecimal("230.50"),
                conta01,
                "Débito automático"
        );

        pagamentoRepo.save(pagamento01);

        Recebimento recebimento01 = new Recebimento(
                null,
                lancamento03,
                hoje,
                new BigDecimal("5000.00"),
                conta01,
                "Crédito de salário"
        );

        recebimentoRepo.save(recebimento01);

        MovimentoConta movimento01 = new MovimentoConta(
                null,
                conta01,
                hoje,
                new BigDecimal("230.50"),
                "Pagamento conta de energia",
                null,
                "LANCAMENTO_PAGAMENTO"
        );

        MovimentoConta movimento02 = new MovimentoConta(
                null,
                conta01,
                hoje,
                new BigDecimal("5000.00"),
                "Recebimento de salário",
                null,
                "LANCAMENTO_RECEBIMENTO"
        );

        Transferencia transferencia01 = new Transferencia(
                null,
                usuario01,
                conta01,
                conta02,
                hoje,
                new BigDecimal("300.00"),
                "Reserva mensal para poupança"
        );

        transferenciaRepo.save(transferencia01);

        MovimentoConta movimento03 = new MovimentoConta(
                null,
                conta01,
                hoje,
                new BigDecimal("300.00"),
                "Transferência para poupança",
                null,
                "TRANSFERENCIA_DEBITO"
        );

        MovimentoConta movimento04 = new MovimentoConta(
                null,
                conta02,
                hoje,
                new BigDecimal("300.00"),
                "Transferência recebida da conta principal",
                null,
                "TRANSFERENCIA_CREDITO"
        );

        movimentoRepo.saveAll(List.of(movimento01, movimento02, movimento03, movimento04));
    }
}
