package com.example.demo.repositorios;

import com.example.demo.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepositorio extends JpaRepository <Reserva,Long>{

}
