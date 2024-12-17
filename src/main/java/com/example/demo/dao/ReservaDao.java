package com.example.demo.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    private Connection connection;

    public ReservaDAO(Connection connection) {
        this.connection = connection;
    }

    public void agregarReserva(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO reservas (cliente_id, fecha_hora, numero_comensales) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reserva.getClienteId());
            statement.setTimestamp(2, Timestamp.valueOf(reserva.getFechaHora()));
            statement.setInt(3, reserva.getNumeroComensales());
            statement.executeUpdate();
        }
    }

    public List<Reserva> obtenerReservas() throws SQLException {
        String sql = "SELECT * FROM reservas";
        List<Reserva> reservas = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id"));
                reserva.setClienteId(rs.getInt("cliente_id"));
                reserva.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                reserva.setNumeroComensales(rs.getInt("numero_comensales"));
                reservas.add(reserva);
            }
        }
        return reservas;
    }

    public void actualizarReserva(Reserva reserva) throws SQLException {
        String sql = "UPDATE reservas SET fecha_hora = ?, numero_comensales = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, Timestamp.valueOf(reserva.getFechaHora()));
            statement.setInt(2, reserva.getNumeroComensales());
            statement.setInt(3, reserva.getId());
            statement.executeUpdate();
        }
    }

    public void eliminarReserva(int id) throws SQLException {
        String sql = "DELETE FROM reservas WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
