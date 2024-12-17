package com.example.demo.controladores;

import com.example.demo.entities.Cliente;

import com.example.demo.servicios.ClienteServicio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteControlador {

    @GetMapping
    public List <Cliente> tomarClientes  (){

    }
}
