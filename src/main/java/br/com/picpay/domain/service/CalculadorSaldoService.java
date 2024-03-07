package br.com.picpay.domain.service;

import br.com.picpay.domain.repository.TransfereciaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculadorSaldoService {

    private final TransfereciaRepository transfereciaRepository;

    public BigDecimal calcularSaldo(UUID usuarioId) {
        BigDecimal recebimentos = transfereciaRepository.sumValorTransferenciaByUsuarioRecebimento(usuarioId);
        BigDecimal transferencias = transfereciaRepository.sumValorTransferenciaByUsuarioTransferencia_Id(usuarioId);

        return recebimentos.subtract(transferencias);
    }

}
