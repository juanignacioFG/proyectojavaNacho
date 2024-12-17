package com.example.demo.repositorios;

import com.example.demo.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio  extends JpaRepository <Cliente,Long> {
}
