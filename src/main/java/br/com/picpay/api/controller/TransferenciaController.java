package br.com.picpay.api.controller;

import br.com.picpay.api.model.input.TransferenciaInput;
import br.com.picpay.domain.service.CadastroTransferenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transferencias")
public class TransferenciaController {

    private final CadastroTransferenciaService cadastroTransferenciaService;

    @PostMapping
    public ResponseEntity<Void> transferir(@RequestBody TransferenciaInput input) {
        cadastroTransferenciaService.realizarTransferencia(input);
        return ResponseEntity.status(CREATED).build();
    }

}
