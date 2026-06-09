package com.Senai.Filmes.Service;

import com.Senai.Filmes.DTO.Request.SessaoRequest;
import com.Senai.Filmes.DTO.Response.FilmeResponse;
import com.Senai.Filmes.DTO.Response.SalaResponse;
import com.Senai.Filmes.DTO.Response.SessaoResponse;
import com.Senai.Filmes.Model.Sala;
import com.Senai.Filmes.Model.Sessao;
import com.Senai.Filmes.Repository.IFilmeRepository;
import com.Senai.Filmes.Repository.ISalaRepository;
import com.Senai.Filmes.Repository.ISessaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SessaoService {

    @Autowired
    private ISessaoRepository sessaoRepository;

    @Autowired
    private IFilmeRepository filmeRepository;

    @Autowired
    private ISalaRepository salaRepository;

    private SessaoResponse toResponse(Sessao sessao){
        FilmeResponse filme = new FilmeResponse(
          sessao.getFilme().getId(),
          sessao.getFilme().getTitulo(),
          sessao.getFilme().getDescricao(),
          sessao.getFilme().getUrlPoster(),
          sessao.getFilme().getGenero(),
          sessao.getFilme().getDuracaoMinutos()
        );

        return new SessaoResponse(
                sessao.getSala().getId(),
                filme,
                sessao.getInicio(),
                sessao.getFim(),
                sessao.getPreco()
        );
    }

    public List<Sessao> listaSessoes(){
        return sessaoRepository.findAll();
    }

    public SessaoResponse criarSessao(@RequestBody SessaoRequest request){
        Sessao sessao = new Sessao();
        sessao.setInicio(request.inicio());
        sessao.setFim(request.fim());
        sessao.setFilme(filmeRepository.getReferenceById(request.filmeId()));
        sessao.setSala(salaRepository.getReferenceById(request.salaId()));
        sessao.setPreco(request.preco());

        return toResponse(sessaoRepository.save(sessao));
    }

    public List<SessaoResponse> listarSessao(){
        return sessaoRepository.findAll().stream().map(sessao -> toResponse(sessao)).toList();
    }

    public List<SessaoResponse> listarSalasPorData(LocalDateTime inicio, LocalDateTime fim){
        List<SessaoResponse> sessoes = sessaoRepository.findByData(inicio, fim).stream().map(sessao -> toResponse(sessao)).toList();
        return sessoes;
    }

    public SessaoResponse buscarPorID(UUID id){
        Sessao sessao = sessaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada"));

        return toResponse(sessao);
    }

    public void deletarSessao(UUID id){
        Sessao sessao = sessaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sessão não encontrada"));

        sessaoRepository.delete(sessao);
    }
}
