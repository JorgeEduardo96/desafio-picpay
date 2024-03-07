package br.com.picpay.api.controller;

import br.com.picpay.api.model.dto.ExtratoUsuarioDto;
import br.com.picpay.api.model.dto.SenhaUsuarioDto;
import br.com.picpay.domain.service.ExtratoUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/extrato/{usuarioId}")
public class ExtratoUsuarioController {

    private final ExtratoUsuarioService extratoUsuarioService;

    @PutMapping
    public ExtratoUsuarioDto getExtratoUsuario(@PathVariable UUID usuarioId, @RequestBody SenhaUsuarioDto senhaUsuarioDto) {
        return extratoUsuarioService.extratoUsuario(usuarioId, senhaUsuarioDto.getSenha());
    }

}
