package br.com.picpay.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_transferencia_id", nullable = false)
    private Usuario usuarioTransferencia;

    @ManyToOne
    @JoinColumn(name = "usuario_recebimento_id", nullable = false)
    private Usuario usuarioRecebimento;

    private BigDecimal valorTransferencia;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataTransferencia;

    private boolean notificacaoEnviada;

    public void confirmarNotificacaoTransferencia() {
        this.notificacaoEnviada = true;
    }

}
