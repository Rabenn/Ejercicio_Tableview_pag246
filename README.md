# Ejercicio TableView

**Resumen:**  
Proyecto JavaFX que permite gestionar una tabla de personas de manera interactiva. Se puede **añadir**, **borrar** y **restaurar** filas, mostrando información básica: Id, nombre, apellido y fecha de nacimiento. Utiliza JavaFX para la interfaz y SLF4J para logging.

## Descripción del proyecto
El proyecto organiza la lógica y la interfaz de manera modular:

- `App.java`: Clase principal que lanza la aplicación y carga el FXML.  
- `Lanzador.java`: Clase de arranque opcional para iniciar la aplicación.  
- `Controlador_Tabla.java`: Controlador de la interfaz, maneja eventos de los botones y la interacción con la tabla.  
- `Persona.java`: Modelo que representa cada fila de la tabla.  
- Recursos FXML y CSS: Definen la estructura y el estilo de la interfaz gráfica.  

## Usos
**Mientras que la opccion principal es abrir IntelliJ IDEA u otro IDE y usar la funcion Package para crear un ejecutable y usarlo hay mas opcciones de ejecución:**
1. Abrir el proyecto en **IntelliJ IDEA**.  
2. Ejecutar la clase `Lanzador` o `App`.  
3. Interactuar con la tabla usando los botones:  

   - **Add**: Añade una fila con los datos ingresados.  
   - **Delete**: Elimina la fila seleccionada.  
   - **Restore**: Restaura la última fila eliminada.
