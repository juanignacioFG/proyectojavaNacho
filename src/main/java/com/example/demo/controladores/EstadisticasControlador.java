/*   package com.example.demo.controladores;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/stadisticas")

public class EstadisticasControlador {
    public EstadisticasControlador(EstadisticasServivicio estadisticasServivicio) {
        this.estadisticasServicio = estadisticasServivicio;
    }
    @GetMapping("/global")
    public ResponseEntity<Map<String, Integer>>verEstadisticasGlobales(){
        Map<String, Integer> estadisticas = EstadisticasServicio.verEstadisticasGlobales();
        return new ResponseEntity<>(estadisticas, HttpStatus.OK);
    }
}
  //FALTA CREAR SERVICIODEESTADISTICAS //hecho    */