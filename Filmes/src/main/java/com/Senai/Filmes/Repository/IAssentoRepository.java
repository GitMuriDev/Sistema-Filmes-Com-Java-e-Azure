package com.Senai.Filmes.Repository;

import com.Senai.Filmes.Model.Assento;
import com.Senai.Filmes.Model.Filme;
import com.Senai.Filmes.Model.ReservaAssento;
import com.Senai.Filmes.Model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IAssentoRepository extends JpaRepository<Assento, UUID> {

    List<Assento> findBySalaId(UUID salaId);
}
