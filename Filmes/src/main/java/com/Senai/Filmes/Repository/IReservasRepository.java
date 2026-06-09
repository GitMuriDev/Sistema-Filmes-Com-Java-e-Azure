package com.Senai.Filmes.Repository;

import com.Senai.Filmes.Model.Reservas;
import com.Senai.Filmes.Model.Sala;
import com.Senai.Filmes.Model.Sessao;
import com.Senai.Filmes.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.JpqlQueryBuilder;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IReservasRepository extends JpaRepository<Reservas, UUID> {

    @Query("SELECT COUNT(r) > 0 FROM Reservas r JOIN r.assentos ra" +
            " WHERE r.sessao.id = :sessaoID AND ra.assento.id = :assentoID" +
            " AND r.status <> 'CANCELADO'")
    boolean verificarAssentos(@Param("sessaoID") UUID SessaoID, @Param("assentoID") UUID assentoID);

}
