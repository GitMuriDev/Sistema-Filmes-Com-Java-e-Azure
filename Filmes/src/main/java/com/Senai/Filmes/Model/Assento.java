package com.Senai.Filmes.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "assentos")

public class Assento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "A fileira é obrigatória")
    private String fileira;

    @NotNull(message = "O número do assento é obrigatório")
    private Integer numero;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;

    @NotNull
    private Boolean status;
}
