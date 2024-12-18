package com.example.demo.controladores;

import com.example.demo.entities.Cliente;
import com.example.demo.repositorios.ClienteRepositorio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteControlador {

    private final ClienteRepositorio clienteRepositorio;

    public ClienteControlador(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    // Obtener todos los clientes
    @GetMapping
    public List<Cliente> verTodosLosClientes() {
        return this.clienteRepositorio.findAll();
    }

    // Obtener un cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> verClientePorId(@PathVariable Long id) {
        Optional<Cliente> cliente = this.clienteRepositorio.findById(id);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Crear un nuevo cliente
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente nuevoCliente) {
        Cliente clienteGuardado = this.clienteRepositorio.save(nuevoCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteGuardado);
    }

    // Actualizar un cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteActualizado) {
        return this.clienteRepositorio.findById(id).map(cliente -> {
            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setApellido(clienteActualizado.getApellido());
            cliente.setTelefono(clienteActualizado.getTelefono());
            Cliente clienteGuardado = this.clienteRepositorio.save(cliente);
            return ResponseEntity.ok(clienteGuardado);
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Eliminar un cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        if (clienteRepositorio.existsById(id)) {
            clienteRepositorio.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}





