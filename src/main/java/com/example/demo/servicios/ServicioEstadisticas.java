/*   package com.example.demo.servicios;

import com.example.demo.repositorios.ClienteRepositorio;
import com.example.demo.repositorios.ReservaRepositorio;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

public class ServicioEstadisticas {
    private final ReservaRepositorio reservaRepositorio;
    private final ClienteRepositorio clienteRepositorio;
    private final com.example.demo.repositorios.ReservaRepositorio ReservaRepositorio;

    public ServicioEstadisticas(ClienteRepositorio clienteRepositorio, ReservaRepositorio reservaRepositorio, ReservaRepositorio reservaRepositorio1, ClienteRepositorio clienteRepositorio1) {
        this.reservaRepositorio = reservaRepositorio1;
        this.clienteRepositorio = clienteRepositorio1;
        this.ReservaRepositorio = reservaRepositorio;

    }
    public Map<String, Integer>VerEstadisticasGlobales(){
        Map<String, Integer> estadisticas = new HashMap<>();
        estadisticas.put("reservas",(int) reservaRepositorio.count());
        estadisticas.put("clientes",(int) clienteRepositorio.count());
        return estadisticas;
    }
}     */
