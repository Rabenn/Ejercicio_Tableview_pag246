# Proyecto: Ejemplo Personas - BBDD

## Descripción

Aplicación JavaFX para gestionar personas en una base de datos.  

Permite:

- Añadir, eliminar y restaurar registros de personas.
- Visualizar los registros en una tabla interactiva.
- Internacionalización con soporte para Español (`es`), Inglés (`en`) y Euskera (`eu`).
- Conexión a base de datos configurable mediante un archivo `configuration.properties`.

---

## Tecnologías

- Java 24
- JavaFX 24
- Maven (gestión de dependencias)
- Base de datos relacional (MySQL, PostgreSQL, H2, etc.)
- FXML y CSS para la interfaz
- Internacionalización con `ResourceBundle`

---

## Estructura del proyecto

src
└── main
├── java
│ └── es.ruben
│ ├── App.java # Clase principal de la aplicación
│ ├── Lanzador.java # (Opcional) clase para lanzar App
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

## Configuración de la base de datos

Archivo: `configuration.properties`

```properties
db.url=jdbc:mysql://localhost:3306/mi_base
db.user=usuario
db.password=contraseña

##Funcionalidad

Crear un archivo de configuraciones o usar el generico puesto en el ejercicio
