package br.com.picpay.domain.service;

import br.com.picpay.api.model.input.TransferenciaInput;
import br.com.picpay.domain.enumeration.TipoTransferencia;
import br.com.picpay.domain.model.Transferencia;
import br.com.picpay.domain.model.Usuario;
import br.com.picpay.domain.repository.TransfereciaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static br.com.picpay.util.FormatadorMonetarioUtil.formatarParaMonetario;

@Service
@RequiredArgsConstructor
@Slf4j
public class CadastroTransferenciaService {

    private final CadastroUsuarioService cadastroUsuarioService;
    private final ValidarUsuarioTipoTransferenciaService validarUsuarioTipoTransferenciaService;
    private final CalculadorSaldoService calculadorSaldoService;
    private final ValidarSaldoTransferencia validarSaldoTransferencia;
    private final AutorizadorService autorizadorService;
    private final TransfereciaRepository transfereciaRepository;

    @Transactional
    public void realizarTransferencia(TransferenciaInput transferenciaInput) {
        Usuario usuarioTransferencia = cadastroUsuarioService.buscarOuFalhar(transferenciaInput.getUsuarioTransferencia().getIdUsuario());
        Usuario usuarioRecebimento = cadastroUsuarioService.buscarOuFalhar(transferenciaInput.getUsuarioRecebimento().getIdUsuario());
        log.info("Transferência do cliente {} para {} com o valor de R$ {}", usuarioTransferencia.getNomeCompleto(),
                usuarioRecebimento.getNomeCompleto(), formatarParaMonetario(transferenciaInput.getValorTransferencia(), "pt", "BR"));

        BigDecimal saldoUsuarioTransferencia = calculadorSaldoService.calcularSaldo(usuarioTransferencia.getId());
        log.info("Cliente {} tem um saldo disponível de R$ {}",
                usuarioTransferencia.getNomeCompleto(), formatarParaMonetario(saldoUsuarioTransferencia, "pt", "BR"));

        validarUsuarioTipoTransferenciaService.isUsuarioAptoTipoTransferencia(usuarioTransferencia, TipoTransferencia.TRANSFERIR);
        validarSaldoTransferencia.validarSaldoUsuario(saldoUsuarioTransferencia, transferenciaInput.getValorTransferencia(), usuarioTransferencia.getNomeCompleto());
        log.info("As validações de isUsuarioAptoTipoTransferencia e validarSaldoUsuario foram relizadas com sucesso.");

        Transferencia transferencia = buildTransferencia(usuarioTransferencia, usuarioRecebimento, transferenciaInput.getValorTransferencia());

        autorizadorService.isAutorizado();
        log.info("Autorização de serviço externo feita com sucesso.");
        transferencia.notificarTransferencia();

        transfereciaRepository.save(transferencia);
        log.info("Transferência realizada com sucesso!");
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

}
