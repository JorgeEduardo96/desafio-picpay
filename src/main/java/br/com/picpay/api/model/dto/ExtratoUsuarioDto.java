package br.com.picpay.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtratoUsuarioDto {

    private List<TransferenciaExtratoDto> recebidas;
    private BigDecimal totalRecebidas;
    private List<TransferenciaExtratoDto> enviadas;
    private BigDecimal totalEnviadas;
    private OffsetDateTime dataExtrato;
    private String nomeCompleto;

}
