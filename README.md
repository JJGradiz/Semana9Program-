```
PracticaHilosJavaFX/
├── src/main/java/
│   └── ni/edu/uam/practicahilos/practicahilosjavafx/
│       ├── Launcher.java                 # Clase de arranque alternativo
│       ├── MainApp.java                  # Punto de entrada principal de JavaFX
│       └── ProcesadorController.java     # Lógica de concurrencia y control de la vista
├── src/main/resources/
│   └── ni/edu/uam/practicahilos/practicahilosjavafx/
│       └── procesador-view.fxml          # Diseño de la interfaz gráfica
├── imagenes/                             # Carpeta para las capturas de pantalla (¡Añadir!)
├── mvnw / mvnw.cmd                       # Maven Wrapper
└── pom.xml                               # Configuración de dependencias Maven
```
# JavaFX_Hilos_Practica

Aplicación de escritorio desarrollada en Java y JavaFX que simula el procesamiento de registros empresariales en segundo plano. Implementa programación concurrente mediante `Thread` y `Runnable` para garantizar que la interfaz gráfica (GUI) permanezca totalmente receptiva durante tareas prolongadas.



## Caso de Estudio
La empresa **NicaData Solutions S.A.** requiere una aplicación que procese registros de clientes sin bloquear la interfaz. La aplicación debe permitir iniciar, detener de forma controlada y reiniciar el proceso, mostrando el avance en tiempo real mediante una barra de progreso.



## Características Principales
- Ejecución de tareas en segundo plano sin congelar la ventana (JavaFX Application Thread).
- Actualización segura de controles gráficos usando `Platform.runLater()`.
- Gestión del ciclo de vida del hilo: Inicio, detención controlada (`interrupt()`) y reinicio.
- Manejo de cierre de ventana: El hilo se detiene automáticamente al cerrar la aplicación (`setOnCloseRequest`).
- **(Reto Final)** Monitor de procesamiento dual: Ejecución simultánea e independiente de dos hilos (Clientes y Productos) con sus propias barras de progreso y controles.

# Capturas del funcionamiento.

<img width="415" height="338" alt="image" src="https://github.com/user-attachments/assets/866f5bf4-8836-4f04-9110-7c2ae1c18e4e" />


<img width="415" height="338" alt="image" src="https://github.com/user-attachments/assets/26420e9d-70d6-4636-aa22-cad80ffda012" />


<img width="413" height="338" alt="image" src="https://github.com/user-attachments/assets/fbff1b1d-afda-45cb-be0a-a6f592aaf487" />


<img width="409" height="334" alt="image" src="https://github.com/user-attachments/assets/b3d64d2b-c3f5-4a58-98d4-7d26f8b5ba09" />



