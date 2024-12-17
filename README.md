# Proyecto de Reservas de Restaurante

Este proyecto es una aplicación Java para gestionar reservas en un restaurante que está abierto de **Miércoles a Domingo**, de **13:00 a 21:00**. El sistema permite a los clientes realizar reservas, gestionar la disponibilidad de las mesas y almacenar información sobre los clientes y las reservas en una base de datos.

## Características

- **Gestión de Clientes**:
    - Registro de clientes con su nombre, apellido y número de teléfono.

- **Gestión de Reservas**:
    - Los clientes pueden hacer reservas, especificando el número de comensales y la fecha y hora de la reserva.
    - Validación de las reservas según el horario permitido (Miércoles a Domingo, de 13:00 a 21:00).
    - Validación de que el número de comensales no sea mayor a 10.

- **Base de Datos**:
    - Utiliza MySQL como sistema de gestión de base de datos.
    - Se gestionan dos tablas principales: `clientes` y `reservas`.

## Estructura del Proyecto

```bash
src/
  ├── model/         (Clases Cliente, Reserva)
  ├── dao/           (Acceso a datos: ClienteDAO, ReservaDAO)
  ├── service/       (Lógica de negocio: Validaciones)
  ├── app/           (Punto de entrada principal)