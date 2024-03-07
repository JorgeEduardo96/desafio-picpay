package br.com.picpay.domain.model;

import br.com.picpay.domain.event.TransferenciaNotificadaEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transferencia extends AbstractAggregateRoot<Transferencia> {

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

    private Boolean notificacaoEnviada = false;

    public void notificarTransferencia() {
        setNotificacaoEnviada(true);
        registerEvent(new TransferenciaNotificadaEvent(this));
    }

}
