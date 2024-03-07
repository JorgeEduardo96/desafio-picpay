package br.com.picpay.domain.service;

import br.com.picpay.api.model.dto.ExtratoUsuarioDto;
import br.com.picpay.api.model.dto.TransferenciaExtratoDto;
import br.com.picpay.domain.model.Transferencia;
import br.com.picpay.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExtratoUsuarioService {

    private final CadastroUsuarioService cadastroUsuarioService;
    private final ValidarSenhaUsuarioService validarSenhaUsuarioService;
    private final CadastroTransferenciaService cadastroTransferenciaService;


    public ExtratoUsuarioDto extratoUsuario(UUID usuarioId, String senhaUsuario) {
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
        validarSenhaUsuarioService.isSenhaUsuarioCorreta(senhaUsuario, usuario.getSenha());
        List<Transferencia> recebimentosModel = cadastroTransferenciaService.getAllTransfereciasByUsuarioRecebimentoId(usuarioId);
        List<Transferencia> transferenciasModel = cadastroTransferenciaService.getAllTransfereciasByUsuarioTransferecniaId(usuarioId);

        List<TransferenciaExtratoDto> recebimentos = null;
        BigDecimal totalRecebimentos = BigDecimal.ZERO;
        List<TransferenciaExtratoDto> transferencias = null;
        BigDecimal totalTransferencias = BigDecimal.ZERO;

        if (!recebimentosModel.isEmpty()) {
            recebimentos = recebimentosModel.stream().map(recebimento -> TransferenciaExtratoDto
                            .builder()
                            .usuario(recebimento.getUsuarioTransferencia().getNomeCompleto())
                            .valor(recebimento.getValorTransferencia())
                            .dataTransferencia(recebimento.getDataTransferencia())
                            .build()).toList();

            totalRecebimentos = recebimentos.stream().map(TransferenciaExtratoDto::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        if (!transferenciasModel.isEmpty()) {
            transferencias = transferenciasModel.stream().map(recebimento -> TransferenciaExtratoDto
                            .builder()
                            .usuario(recebimento.getUsuarioRecebimento().getNomeCompleto())
                            .valor(recebimento.getValorTransferencia())
                            .dataTransferencia(recebimento.getDataTransferencia())
                            .build()).toList();

            totalTransferencias = transferencias.stream().map(TransferenciaExtratoDto::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        return buildExtratoUsuarioDto(transferencias, totalTransferencias, recebimentos, totalRecebimentos);
    }

    private ExtratoUsuarioDto buildExtratoUsuarioDto(List<TransferenciaExtratoDto> transferencias,
                                                     BigDecimal totalTransferencias,
                                                     List<TransferenciaExtratoDto> recebimentos,
                                                     BigDecimal totalRecebimentos) {
        return ExtratoUsuarioDto.builder()
                .dataExtrato(OffsetDateTime.now())
                .enviadas(transferencias)
                .totalEnviadas(totalTransferencias)
                .recebidas(recebimentos)
                .totalRecebidas(totalRecebimentos)
                .build();
    }

}
