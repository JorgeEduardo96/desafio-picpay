package br.com.picpay.api.controller;

import br.com.picpay.api.model.dto.UsuarioDto;
import br.com.picpay.api.model.input.UsuarioInput;
import br.com.picpay.domain.mapper.UsuarioMapper;
import br.com.picpay.domain.service.CadastroUsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {

    private final CadastroUsuarioService cadastroUsuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    public ResponseEntity<UsuarioDto> save(@Valid @RequestBody UsuarioInput input) {
        return ResponseEntity.status(CREATED).body(usuarioMapper.toDto(cadastroUsuarioService.save(input)));
    }

    @GetMapping
    public List<UsuarioDto> getAll() {
        return usuarioMapper.toCollectionDto(cadastroUsuarioService.getAll());
    }

}
