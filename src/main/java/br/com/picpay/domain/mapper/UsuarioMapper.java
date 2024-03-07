package br.com.picpay.domain.mapper;

import br.com.picpay.api.model.dto.UsuarioDto;
import br.com.picpay.api.model.input.UsuarioInput;
import br.com.picpay.domain.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    public Usuario toModel(UsuarioInput input) {
        return Usuario.builder()
                .email(input.getEmail())
                .tipoUsuario(input.getTipoUsuario())
                .nomeCompleto(input.getNomeCompleto())
                .senha(input.getSenha())
                .cpfCnpj(input.getCpfCnpj())
                .build();
    }

    public UsuarioDto toDto(Usuario usuarioModel) {
        return UsuarioDto.builder()
                .id(usuarioModel.getId())
                .email(usuarioModel.getEmail())
                .nomeCompleto(usuarioModel.getNomeCompleto())
                .tipoUsuario(usuarioModel.getTipoUsuario())
                .cpfCnpj(usuarioModel.getCpfCnpj())
                .dataCadastro(usuarioModel.getDataCadastro())
                .build();
    }

    public List<UsuarioDto> toCollectionDto(List<Usuario> usuarioModelList) {
        return usuarioModelList.stream().map(this::toDto).collect(Collectors.toList());
    }

}
