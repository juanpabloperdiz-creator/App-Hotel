# 🏨 HotelApp

¡Bienvenido a **HotelApp**! Una aplicación de escritorio desarrollada en **Java con JavaFX** y **FXML** que permite gestionar y visualizar el estado de las habitaciones de un hotel cargando los datos directamente desde un archivo XML.

---

## ✨ Características principales

* 📋 **Listado visual avanzado**: Muestra las habitaciones mediante tarjetas personalizadas con indicadores de color según su estado (disponible, ocupada o en mantenimiento).
* 🔍 **Buscador rápido**: Permite buscar una habitación por su número exacto para comprobar su estado al instante y resaltarla en la lista.
* 🪟 **Ventana de detalles**: Al hacer clic en "Ver detalles" en cualquier tarjeta, se abre una ventana modal con información completa (planta, tipo, capacidad, precio por noche, vistas y accesibilidad).
* 📄 **Carga de datos por XML**: Toda la información del hotel y sus habitaciones se lee y procesa de manera automática desde un archivo estructurado en XML (`reservas.xml`).

---

## 🛠️ Tecnologías utilizadas

* **Java** (JDK 17 o superior recomendado)
* **JavaFX** (Interfaz gráfica de usuario y FXML)
* **DOM Parser** (Para la lectura y procesamiento del archivo XML)

---

## 🚀 Estructura del proyecto

* `Main.java`: Clase principal que inicia la aplicación y carga la vista de JavaFX.
* `Controlador.java`: Gestiona la lógica de la interfaz, la lectura del XML y las búsquedas.
* `Habitacion.java`: Modelo de datos que representa cada habitación del hotel.
* `TarjetaHabitacion.java`: Componente visual personalizado para cada elemento de la lista.
* `vista.fxml`: Estructura visual de la interfaz de usuario.
* `reservas.xml`: Archivo de datos con la información del hotel, habitaciones y reservas.
