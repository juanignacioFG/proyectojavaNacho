package com.example.demo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservaDto(@NotNull(message = "Date cannot be null")
                         LocalDate date,

                         @NotNull(message = "Time cannot be null")
                         LocalTime time,

                         @NotBlank(message = "Reason cannot be blank")
                         @Size(max = 255, message = "Reason must be less than 255 characters")
                         @Pattern(regexp = "^[a-zA-Z\\s\\p{Punct}]+$", message = "Reason must contain only letters and spaces")
                         String reason,)




    {
        public  Reserva (Reserva) {
            private int id;
            private int clienteId;
            private LocalDateTime fechaHora;
            private int numeroComensales;
        public Appointment toEntity(Pet pet) {
            return new Appointment(
                    this.date,
                    this.time,
                    this.reason,
                    pet
            );
        }
    }

