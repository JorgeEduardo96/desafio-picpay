package br.com.picpay.api.controller;

import br.com.picpay.api.model.dto.SenhaUsuarioDto;
import br.com.picpay.domain.service.SaldoUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/saldo/{usuarioId}")
public class SaldoUsuarioController {

    private final SaldoUsuarioService saldoUsuarioService;

    @PutMapping
    public String saldoUsuario(@PathVariable UUID usuarioId, @RequestBody SenhaUsuarioDto senhaUsuarioDto) {
        return saldoUsuarioService.saldoUsuario(usuarioId, senhaUsuarioDto.getSenha());
    }

}
