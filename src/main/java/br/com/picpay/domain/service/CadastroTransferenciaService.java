package br.com.picpay.domain.service;

import br.com.picpay.api.model.input.TransferenciaInput;
import br.com.picpay.domain.event.TransferenciaNotificadaEvent;
import br.com.picpay.domain.model.Transferencia;
import br.com.picpay.domain.model.Usuario;
import br.com.picpay.domain.model.exception.TransferenciaNaoEncontradaException;
import br.com.picpay.domain.repository.TransfereciaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static br.com.picpay.util.FormatadorMonetarioUtil.formatarParaMonetario;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
@RequiredArgsConstructor
@Slf4j
public class CadastroTransferenciaService {

    private final CadastroUsuarioService cadastroUsuarioService;
    private final ValidadorTransferenciaService validadorTransferenciaService;
    private final CalculadorSaldoService calculadorSaldoService;
    private final AutorizadorService autorizadorService;
    private final TransfereciaRepository transfereciaRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void realizarTransferencia(TransferenciaInput transferenciaInput) {
        Usuario usuarioTransferencia = cadastroUsuarioService.buscarOuFalhar(transferenciaInput.getUsuarioTransferencia().getIdUsuario());
        Usuario usuarioRecebimento = cadastroUsuarioService.buscarOuFalhar(transferenciaInput.getUsuarioRecebimento().getIdUsuario());
        log.info("Transferência do cliente {} para {} com o valor de R$ {}", usuarioTransferencia.getNomeCompleto(),
                usuarioRecebimento.getNomeCompleto(), formatarParaMonetario(transferenciaInput.getValorTransferencia(), "pt", "BR"));

        BigDecimal saldoUsuarioTransferencia = calculadorSaldoService.calcularSaldo(usuarioTransferencia.getId());
        log.info("Cliente {} tem um saldo disponível de R$ {}",
                usuarioTransferencia.getNomeCompleto(), formatarParaMonetario(saldoUsuarioTransferencia, "pt", "BR"));

        validadorTransferenciaService.execute(usuarioTransferencia, saldoUsuarioTransferencia, transferenciaInput);

        Transferencia transferencia = buildTransferencia(usuarioTransferencia, usuarioRecebimento, transferenciaInput.getValorTransferencia());

        autorizadorService.isAutorizado();
        transferencia = transfereciaRepository.save(transferencia);
        log.info("Transferência realizada com sucesso!");
        eventPublisher.publishEvent(new TransferenciaNotificadaEvent(transferencia));
    }

    private Transferencia buildTransferencia(Usuario usuarioTransferencia, Usuario usuarioRecebimento, BigDecimal valorTransferencia) {
        return Transferencia.builder()
                .usuarioTransferencia(usuarioTransferencia)
                .usuarioRecebimento(usuarioRecebimento)
                .valorTransferencia(valorTransferencia)
                .dataTransferencia(OffsetDateTime.now())
                .build();
    }

    public List<Transferencia> getAllTransfereciasByUsuarioTransferecniaId(UUID usuarioTransferenciaId) {
        return transfereciaRepository.findByUsuarioTransferencia(cadastroUsuarioService.buscarOuFalhar(usuarioTransferenciaId));
    }

    public List<Transferencia> getAllTransfereciasByUsuarioRecebimentoId(UUID usuarioRecebimentoId) {
        return transfereciaRepository.findByUsuarioRecebimento(cadastroUsuarioService.buscarOuFalhar(usuarioRecebimentoId));
    }

    public List<Transferencia> getAllTransferenciaNaoNotificadas() {
        return transfereciaRepository.findByNotificacaoEnviada(false);
    }

    public Transferencia buscarOuFalhar(UUID id) {
        return transfereciaRepository.
                findById(id).orElseThrow(() -> new TransferenciaNaoEncontradaException(id));
    }

    @Transactional(propagation = REQUIRES_NEW)
    public void confirmacaoNotifacaoTransferencia(UUID transferenciaId) {
        Transferencia transferenciaModel = buscarOuFalhar(transferenciaId);
        transferenciaModel.confirmarNotificacaoTransferencia();
    }

}
