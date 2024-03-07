package br.com.picpay.domain.service;

import br.com.picpay.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.UUID;

import static br.com.picpay.util.FormatadorMonetarioUtil.formatarParaMonetario;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class SaldoUsuarioService {

    private final CalculadorSaldoService calculadorSaldoService;
    private final CadastroUsuarioService cadastroUsuarioService;
    private final ValidarSenhaUsuarioService validarSenhaUsuarioService;

    public String saldoUsuario(UUID id, String senhaUsuario) {
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(id);
        validarSenhaUsuarioService.isSenhaUsuarioCorreta(senhaUsuario, usuario.getSenha());

        return format("Olá %s, seu saldo atual é: R$ %s", usuario.getNomeCompleto(),
                formatarParaMonetario(calculadorSaldoService.calcularSaldo(id), "pt", "BR"));
    }

}
