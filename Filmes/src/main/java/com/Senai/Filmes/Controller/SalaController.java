package com.Senai.Filmes.Controller;


import com.Senai.Filmes.DTO.Request.SalaRequest;
import com.Senai.Filmes.DTO.Response.SalaResponse;
import com.Senai.Filmes.Service.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @PostMapping
    @Operation(summary = "Criar sala", description = "Retorna a criação de uma sala no sistema")
    public ResponseEntity<SalaResponse> criarSala(@RequestBody SalaRequest salaRequest){
        return new ResponseEntity<>(salaService.cadastrarSala(salaRequest), HttpStatus.CREATED);
    }

    //Listar Sala
    @GetMapping
    @Operation(summary = "Listar salas", description = "Ele lista todas as salas")
    public ResponseEntity<List<SalaResponse>> listarTodos(){
        List<SalaResponse> salas = salaService.listarTodos();
        if(salas.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(salas, HttpStatus.OK);
    }

    //Atualizar Sala

}
