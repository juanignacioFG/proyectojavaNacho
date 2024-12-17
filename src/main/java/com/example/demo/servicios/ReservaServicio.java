package com.example.demo.servicios;

import com.example.demo.entities.Reserva;
import com.example.demo.sqlconexion.DatabaseConnection;

import java.sql.*;

public Reserva ReservaServicio(Reserva reserva) {
    String sql = "INSERT INTO reservas (cliente_id, fecha_hora, numero_comensales) VALUES (?, ?, ?)";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setInt(1, reserva.getClienteId());
        stmt.setTimestamp(2, Timestamp.valueOf(reserva.getFechaHora()));
        stmt.setInt(3, reserva.getNumeroComensales());
        stmt.executeUpdate();
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            reserva.setId(generatedKeys.getInt(1));
        }
        return reserva;
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}

public Reserva obtenerReservaPorId(int id) {
    String sql = "SELECT * FROM reservas WHERE id = ?";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Reserva(
                    rs.getInt("id"),
                    rs.getInt("cliente_id"),
                    rs.getTimestamp("fecha_hora").toLocalDateTime(),
                    rs.getInt("numero_comensales")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
