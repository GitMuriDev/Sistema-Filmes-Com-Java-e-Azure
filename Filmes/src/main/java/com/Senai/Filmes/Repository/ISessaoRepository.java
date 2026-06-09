package com.Senai.Filmes.Repository;

import com.Senai.Filmes.Model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ISessaoRepository extends JpaRepository<Sessao, UUID> {

    // Busca sessões por data: filtra pelo campo 'inicio' (não 'horaInicio')
    // JPQL usa nomes das classes e campos Java, não das tabelas SQL
    @Query("SELECT s FROM Sessao s WHERE s.inicio >= :inicioDia AND s.inicio < :fimDia")
    List<Sessao> findByData(@Param("inicioDia") LocalDateTime inicioDia,
                            @Param("fimDia") LocalDateTime fimDia);

    // Verifica conflito de horário na mesma sala
    // Lógica: uma sessão conflita se começa antes do fim da nova E termina depois do início
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Sessao s " +
            "WHERE s.sala.id = :salaId AND s.inicio < :fim AND s.fim > :inicio")
    boolean existeConflitoDeSala(@Param("salaId") UUID salaId,
                                 @Param("inicio") LocalDateTime inicio,
                                 @Param("fim") LocalDateTime fim);

    List<Sessao> findByFilmeId(UUID id);

}