package com.Senai.Filmes.Model;
//Ela vai ligar com a tabela seções e a tabela usuário

import com.Senai.Filmes.Model.Enums.StatusReserva;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Data
@NoArgsConstructor // Essa anotation pode existir
@Entity
@Table(name = "reservas")

public class Reservas {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne // Evitar ao máximo Many to One. O ideal é one to many.
    @JoinColumn(name = "usuario_id") // Ele significa que você vai pegar uma informação, uma coluna de outra tabela. Primeiro tem que passar o nome da coluna que é a do ID
    //JoinColumn é a coluna foreign key. O que diferencia daí é que no mysql tem que informar de onde ver, no código você já fala pra onde ele vai
    private Usuario usuario;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sessao_id")
    private Sessao sessao;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusReserva status;

    @CreationTimestamp
    private LocalDateTime criadoEm;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservaAssento> assentos =  new ArrayList<>();


}
