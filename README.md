# Proyecto: Ejemplo Personas - BBDD

## Descripción

Aplicación **JavaFX 24** para gestionar personas en una base de datos, usando un **DAO asíncrono** (`DaoPersona`) para evitar bloqueos en la interfaz.

Permite:

- Añadir, eliminar y restaurar registros de personas.
- Visualizar los registros en una tabla interactiva y **responsive**.
- Internacionalización con soporte para Español (`es`), Inglés (`en`) y Euskera (`eu`).
- Conexión a base de datos configurable mediante el archivo `configuration.properties`.

---

## Tecnologías

- **Java 24**  
- **JavaFX 24**  
- **Maven** (gestión de dependencias)  
- **Base de datos relacional** (MySQL, PostgreSQL, H2, etc.)  
- **FXML y CSS** para la interfaz  
- **Internacionalización** con `ResourceBundle`  
- **Asincronía** con `CompletableFuture` y pool de hilos

---

## Estructura del proyecto
src
└── main
├── java
│ └── es.ruben
│ ├── App.java # Clase principal de la aplicación
│ ├── Lanzador.java # Clase opcional para lanzar App
│ ├── controladores
│ │ └── Controlador_Tabla.java
│ ├── dao
│ │ └── DaoPersona.java
│ ├── modelos
│ │ └── Persona.java
│ └── util
│ ├── Alertas.java
│ └── Propiedades.java
└── resources
└── es.ruben
├── fxml
│ └── table_app_info.fxml
├── css
│ └── estilos.css
├── imagenes
│ └── icono_1.png
├── configuration.properties
├── texto_es.properties
├── texto_en.properties
└── texto_eu.properties

---

---

## Creación de la base de datos

La base de datos debe contener la base DNI y la tabla `persona` con al menos las siguientes columnas:


CREATE TABLE DNI.persona (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    birth_date DATE
);
---
Configuración de la base de datos

Archivo: configuration.properties (ubicado en src/main/resources/):

```properties
# URL de la base de datos (cambia según tu servidor y BD)
db.url=jdbc:mysql://localhost:3306/DNI

# Usuario de la base de datos
db.user=usuario

# Contraseña del usuario
db.password=contraseña


🔹 Ajusta db.user, db.password y db.url según tu entorno.
🔹 Este archivo permite cambiar la conexión sin recompilar la aplicación.
