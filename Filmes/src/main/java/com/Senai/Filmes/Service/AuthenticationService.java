package com.Senai.Filmes.Service;

import com.Senai.Filmes.DTO.Request.CadastroRequest;
import com.Senai.Filmes.DTO.Request.LoginRequest;
import com.Senai.Filmes.DTO.Response.AuthResponse;
import com.Senai.Filmes.Model.Enums.Cargo;
import com.Senai.Filmes.Model.Usuario;
import com.Senai.Filmes.Repository.IUsuarioRepository;
import com.Senai.Filmes.Security.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse cadastrarUsuario(CadastroRequest cadastroRequest) {
        if (usuarioRepository.existsByEmail(cadastroRequest.email())){
            throw new RuntimeException("Email invalido");
        }

        Usuario novousuario = new Usuario();
        novousuario.setNome(cadastroRequest.nome());
        novousuario.setEmail(cadastroRequest.email());
        novousuario.setSenha(passwordEncoder.encode(cadastroRequest.senha()));
        novousuario.setCargo(Cargo.USUARIO);

        usuarioRepository.save(novousuario);

        String token = jwtUtil.gerarToken(novousuario);
        return new AuthResponse(token, novousuario.getNome(), novousuario.getCargo().name());

    }

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.senha())
        );

        var usuario = usuarioRepository.findByEmail(loginRequest.email()).orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));

        String token = jwtUtil.gerarToken(usuario);
        return new AuthResponse(token, usuario.getNome(), usuario.getCargo().name());
    }
}
