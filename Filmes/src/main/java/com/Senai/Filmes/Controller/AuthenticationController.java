package com.Senai.Filmes.Controller;

import com.Senai.Filmes.DTO.Request.CadastroRequest;
import com.Senai.Filmes.DTO.Request.LoginRequest;
import com.Senai.Filmes.DTO.Response.AuthResponse;
import com.Senai.Filmes.Model.Usuario;
import com.Senai.Filmes.Repository.IUsuarioRepository;
import com.Senai.Filmes.Service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticação", description = "Endpoint para gerenciamento de autenticação e login de usuário")
@RestController
@CrossOrigin("*")
@RequestMapping("/api/autenticacao")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authentication;


    @PostMapping("/cadastro")
    @Operation(summary = "Criação de usuário", description = "Método usado para cadastrar e validar usuário")
    public ResponseEntity<AuthResponse> cadastrarUsuario(@RequestBody CadastroRequest usuario) {
        return new ResponseEntity<>(authentication.cadastrarUsuario(usuario), HttpStatus.OK);
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Método usado para login de usuário")
    public ResponseEntity<AuthResponse> loginUsuario(@RequestBody LoginRequest login) {
        return new ResponseEntity<>(authentication.login(login), HttpStatus.OK);
    }
}
