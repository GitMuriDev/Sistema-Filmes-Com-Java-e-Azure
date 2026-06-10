package com.Senai.Filmes.Service;

import com.Senai.Filmes.DTO.Request.ReservaRequest;
import com.Senai.Filmes.DTO.Response.*;
import com.Senai.Filmes.Model.*;
import com.Senai.Filmes.Model.Enums.StatusReserva;
import com.Senai.Filmes.Repository.IAssentoRepository;
import com.Senai.Filmes.Repository.IReservasRepository;
import com.Senai.Filmes.Repository.ISessaoRepository;
import com.Senai.Filmes.Repository.IUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReservasService {

    @Autowired
    private IReservasRepository reservasRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ISessaoRepository sessaoRepository;

    @Autowired
    private IAssentoRepository assentoRepository;

    private ReservaResponse toResponse(Reservas reserva){
        FilmeResponse filme = new FilmeResponse(
                reserva.getSessao().getFilme().getId(),
                reserva.getSessao().getFilme().getTitulo(),
                reserva.getSessao().getFilme().getDescricao(),
                reserva.getSessao().getFilme().getUrlPoster(),
                reserva.getSessao().getFilme().getGenero(),
                reserva.getSessao().getFilme().getDuracaoMinutos()
        );

        SessaoResponse sessao = new SessaoResponse(
                reserva.getSessao().getId(),
                filme,
                reserva.getSessao().getInicio(),
                reserva.getSessao().getFim(),
                reserva.getSessao().getPreco()
        );

        List<AssentoResponse> assento = reserva.getAssentos().stream().map( ra -> new AssentoResponse(
                ra.getAssento().getId(),
                ra.getAssento().getFileira(),
                ra.getAssento().getNumero(),
                ra.getAssento().getStatus()
        )).toList();

        return new ReservaResponse(
                reserva.getId(),
                sessao,
                assento,
                reserva.getStatus(),
                reserva.getCriadoEm()
        );
    }

    public List<ReservaResponse> listarReservas(){
        return reservasRepository.findAll().stream().map(francis -> toResponse(francis)).toList();
    }

    @Transactional
    public ReservaResponse criarReserva(ReservaRequest request, UUID idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new EntityNotFoundException("Usuário nao encontrado"));
        Sessao sessao = sessaoRepository.findById(request.sessaoId()).orElseThrow(() -> new  EntityNotFoundException("Sessão não encontrada"));
        Reservas reserva =  new Reservas();
        reserva.setUsuario(usuario);
        reserva.setSessao(sessao);
        reserva.setStatus(StatusReserva.DISPONÍVEL);
        for(UUID assentoid:request.assentoIds()){
            Assento assento = assentoRepository.findById(assentoid).orElseThrow(() -> new EntityNotFoundException("Assento id " + assentoid + " não encontrado"));
            if(!assento.getSala().getId().equals(sessao.getSala().getId())){
                throw new RuntimeException("O assento " + assento.getNumero() + " não pertence a essa sala");
            }
            if (reservasRepository.verificarAssentos(sessao.getId(), assentoid)){
                throw new RuntimeException("O assento " + assento.getFileira() + assento.getNumero() + " está ocupado");
            }

            ReservaAssento reservaAssento = new ReservaAssento();
            reservaAssento.setReserva(reserva);
            reservaAssento.setAssento(assento);

            reserva.getAssentos().add(reservaAssento);


        }

        Reservas reservaSalva = reservasRepository.save(reserva);
        return toResponse(reserva);

    }
}
