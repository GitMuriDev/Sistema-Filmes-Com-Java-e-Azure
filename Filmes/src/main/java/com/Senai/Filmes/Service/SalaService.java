package com.Senai.Filmes.Service;

import com.Senai.Filmes.DTO.Request.SalaRequest;
import com.Senai.Filmes.DTO.Response.SalaResponse;
import com.Senai.Filmes.Model.Assento;
import com.Senai.Filmes.Model.Sala;
import com.Senai.Filmes.Repository.ISalaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SalaService {


    @Autowired
    private ISalaRepository salaRepository;

    public SalaResponse cadastrarSala(SalaRequest request) {
        Sala sala = new Sala();
        sala.setNome(request.nome());
        sala.setTotalAssentos(request.fileiras() * request.assentosPorFileira());

        List<Assento> assentos = gerarAssentos(sala, request.fileiras(), request.assentosPorFileira());
        sala.setAssentos(assentos);

        return toResponse(salaRepository.save(sala));
    }

    public List<SalaResponse> listarTodos(){
        return salaRepository.findAll().stream().map(this::toResponse).toList();
    }

    //get byId Buscar por id
    public SalaResponse buscarPorSalaId(UUID id){
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada!"));

        return toResponse(sala);
    }

    //Atualizar sala

    public SalaResponse atualizarSala(UUID id, SalaRequest salaRequest){
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma sala encontrada"));
        sala.setNome(salaRequest.nome());
        //PAREIAQUI
        sala.setAssentos();
    }

    private List<Assento> gerarAssentos(Sala sala, int fileiras, int assentosPorFileira) {
        List<Assento> assentos = new ArrayList<>();
        for (int f = 0; f < fileiras; f++) {
            String fileira = String.valueOf((char) ('A' + f));
            for (int n = 1; n <= assentosPorFileira; n++) {
                Assento assento = new Assento();
                assento.setSala(sala);
                assento.setFileira(fileira);
                assento.setNumero(n);
                assentos.add(assento);
            }
        }
        return assentos;
    }

    private SalaResponse toResponse(Sala sala) {
        return new SalaResponse(
                sala.getId(),
                sala.getNome(),
                sala.getTotalAssentos()
        );
    }
}
