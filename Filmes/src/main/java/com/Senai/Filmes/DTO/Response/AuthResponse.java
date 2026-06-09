package com.Senai.Filmes.DTO.Response;

public record AuthResponse(
        String token, // VocÊ não mostra pro usuário o token, mas o frontend tem que receber essa informação.
        String nome,
        String cargo

) {
}
