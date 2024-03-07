package br.com.picpay.domain.repository;

import br.com.picpay.domain.model.Transferencia;
import br.com.picpay.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransfereciaRepository extends JpaRepository<Transferencia, UUID> {

    @Query("SELECT COALESCE(SUM(t.valorTransferencia), 0) FROM Transferencia t WHERE t.usuarioRecebimento.id = :usuarioRecebimentoId")
    BigDecimal sumValorTransferenciaByUsuarioRecebimento(@Param("usuarioRecebimentoId") UUID usuarioRecebimentoId);

    @Query("SELECT COALESCE(SUM(t.valorTransferencia), 0) FROM Transferencia t WHERE t.usuarioTransferencia.id = :usuarioTransferenciaId")
    BigDecimal sumValorTransferenciaByUsuarioTransferencia_Id(UUID usuarioTransferenciaId);

    List<Transferencia> findByUsuarioTransferencia(Usuario usuarioTransferencia);
    List<Transferencia> findByUsuarioRecebimento(Usuario usuarioTransferencia);

}
