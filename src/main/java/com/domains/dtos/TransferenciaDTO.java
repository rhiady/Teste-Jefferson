package com.domains.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferenciaDTO {


        public interface Create {}
        public interface Update {}

        @Null(groups = Create.class, message = "Id deve ser omitido na criação")
        @NotNull(groups = Update.class, message = "Id é obrigatório na atualização")
        private Long idTransferencia;

        @NotNull(message = "Usuário é obrigatório")
        private Long usuarioId;
        private String usuarioNome;

        @NotNull(message = "Conta de origem é obrigatória")
        private Long contaOrigemId;
        private String contaOrigemApelido;

        @NotNull(message = "Conta de destino é obrigatória")
        private Long contaDestinoId;
        private String contaDestinoApelido;

        @NotNull(message = "Data é obrigatória")
        private LocalDate data;

        @NotNull(message = "Valor é obrigatório")
        @Digits(integer = 15, fraction = 2, message = "Valor inválido")
        private BigDecimal valor;

        @Size(max = 255, message = "Observação deve ter no máximo 255 caracteres")
        private String observacao;

        public TransferenciaDTO() {}

        public TransferenciaDTO(Long idTransferencia, Long usuarioId, String usuarioNome,
                                Long contaOrigemId, String contaOrigemApelido,
                                Long contaDestinoId, String contaDestinoApelido,
                                LocalDate data, BigDecimal valor, String observacao) {
            this.idTransferencia = idTransferencia;
            this.usuarioId = usuarioId;
            this.usuarioNome = usuarioNome;
            this.contaOrigemId = contaOrigemId;
            this.contaOrigemApelido = contaOrigemApelido;
            this.contaDestinoId = contaDestinoId;
            this.contaDestinoApelido = contaDestinoApelido;
            this.data = data;
            this.valor = valor;
            this.observacao = observacao;
        }

        public Long getIdTransferencia() {
            return idTransferencia;
        }

        public void setIdTransferencia(Long idTransferencia) {
            this.idTransferencia = idTransferencia;
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

        public Long getContaOrigemId() {
            return contaOrigemId;
        }

        public void setContaOrigemId(Long contaOrigemId) {
            this.contaOrigemId = contaOrigemId;
        }

        public String getContaOrigemApelido() {
            return contaOrigemApelido;
        }

        public void setContaOrigemApelido(String contaOrigemApelido) {
            this.contaOrigemApelido = contaOrigemApelido;
        }

        public Long getContaDestinoId() {
            return contaDestinoId;
        }

        public void setContaDestinoId(Long contaDestinoId) {
            this.contaDestinoId = contaDestinoId;
        }

        public String getContaDestinoApelido() {
            return contaDestinoApelido;
        }

        public void setContaDestinoApelido(String contaDestinoApelido) {
            this.contaDestinoApelido = contaDestinoApelido;
        }

        public LocalDate getData() {
            return data;
        }

        public void setData(LocalDate data) {
            this.data = data;
        }

        public BigDecimal getValor() {
            return valor;
        }

        public void setValor(BigDecimal valor) {
            this.valor = valor;
        }

        public String getObservacao() {
            return observacao;
        }

        public void setObservacao(String observacao) {
            this.observacao = observacao;
        }

        @Override
        public String toString() {
            return "TransferenciaDTO{" +
                    "idTransferencia=" + idTransferencia +
                    ", usuarioId=" + usuarioId +
                    ", usuarioNome='" + usuarioNome + '\'' +
                    ", contaOrigemId=" + contaOrigemId +
                    ", contaOrigemApelido='" + contaOrigemApelido + '\'' +
                    ", contaDestinoId=" + contaDestinoId +
                    ", contaDestinoApelido='" + contaDestinoApelido + '\'' +
                    ", data=" + data +
                    ", valor=" + valor +
                    ", observacao='" + observacao + '\'' +
                    '}';
        }
}
