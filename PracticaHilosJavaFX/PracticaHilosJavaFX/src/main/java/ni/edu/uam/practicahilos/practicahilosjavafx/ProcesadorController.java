package ni.edu.uam.practicahilos.practicahilosjavafx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ProcesadorController {

    @FXML
    private Label estadoClientes;
    @FXML
    private Label porcentajeClientes;
    @FXML
    private ProgressBar progresoClientes;
    @FXML
    private Button iniciarClientes;
    @FXML
    private Button detenerClientes;
    @FXML
    private Button reiniciarClientes;

    @FXML
    private Label estadoProductos;
    @FXML
    private Label porcentajeProductos;
    @FXML
    private ProgressBar progresoProductos;
    @FXML
    private Button iniciarProductos;
    @FXML
    private Button detenerProductos;
    @FXML
    private Button reiniciarProductos;

    private ProcessMonitor clientes;
    private ProcessMonitor productos;
    private boolean closing;

    @FXML
    private void initialize() {
        clientes = new ProcessMonitor(
                "Clientes", 100, 100, estadoClientes, porcentajeClientes,
                progresoClientes, iniciarClientes, detenerClientes, reiniciarClientes);
        productos = new ProcessMonitor(
                "Productos", 50, 160, estadoProductos, porcentajeProductos,
                progresoProductos, iniciarProductos, detenerProductos, reiniciarProductos);
    }

    @FXML
    private void iniciarClientes() {
        iniciarProceso(clientes);
    }

    @FXML
    private void detenerClientes() {
        detenerProceso(clientes);
    }

    @FXML
    private void reiniciarClientes() {
        reiniciarProceso(clientes);
    }

    @FXML
    private void iniciarProductos() {
        iniciarProceso(productos);
    }

    @FXML
    private void detenerProductos() {
        detenerProceso(productos);
    }

    @FXML
    private void reiniciarProductos() {
        reiniciarProceso(productos);
    }

    private void iniciarProceso(ProcessMonitor proceso) {
        if (proceso.hilo != null && proceso.hilo.isAlive()) {
            return;
        }

        proceso.numeroEjecucion++;
        int ejecucion = proceso.numeroEjecucion;
        proceso.progreso.setProgress(0);
        proceso.porcentaje.setText("0%");
        proceso.estado.setText("Procesando " + proceso.nombre.toLowerCase() + "...");
        proceso.iniciar.setDisable(true);
        proceso.detener.setDisable(false);
        proceso.reiniciar.setDisable(true);

        Runnable tarea = () -> {
            int avance = 0;
            boolean detenido = false;

            try {
                for (int i = 1; i <= proceso.total; i++) {
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }

                    Thread.sleep(proceso.demoraMs);
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }

                    avance = i;
                    int avanceActual = avance;
                    int porcentaje = avanceActual * 100 / proceso.total;
                    Platform.runLater(() -> {
                        if (!closing && proceso.numeroEjecucion == ejecucion) {
                            proceso.progreso.setProgress((double) avanceActual / proceso.total);
                            proceso.porcentaje.setText(porcentaje + "%");
                        }
                    });
                }
            } catch (InterruptedException e) {
                detenido = true;
            }

            int avanceFinal = avance;
            boolean fueDetenido = detenido;
            Platform.runLater(() -> finalizarProceso(proceso, ejecucion, avanceFinal, fueDetenido));
        };

        proceso.hilo = new Thread(tarea, "Hilo-" + proceso.nombre);
        proceso.hilo.setDaemon(true);
        proceso.hilo.start();
    }

    private void detenerProceso(ProcessMonitor proceso) {
        if (proceso.hilo != null && proceso.hilo.isAlive()) {
            proceso.estado.setText("Solicitando detención...");
            proceso.detener.setDisable(true);
            proceso.hilo.interrupt();
        }
    }

    private void reiniciarProceso(ProcessMonitor proceso) {
        if (proceso.hilo != null && proceso.hilo.isAlive()) {
            return;
        }

        proceso.numeroEjecucion++;
        proceso.hilo = null;
        proceso.progreso.setProgress(0);
        proceso.porcentaje.setText("0%");
        proceso.estado.setText("Listo para iniciar");
        habilitarControles(proceso);
    }

    private void finalizarProceso(ProcessMonitor proceso, int ejecucion,
                                  int avance, boolean detenido) {
        if (closing || proceso.numeroEjecucion != ejecucion) {
            return;
        }

        proceso.estado.setText(detenido
                ? "Proceso detenido en " + (avance * 100 / proceso.total) + "%"
                : "Proceso finalizado");
        proceso.hilo = null;
        habilitarControles(proceso);
    }

    private void habilitarControles(ProcessMonitor proceso) {
        proceso.iniciar.setDisable(false);
        proceso.detener.setDisable(true);
        proceso.reiniciar.setDisable(false);
    }

    public void detenerAlCerrar() {
        closing = true;
        interrumpir(clientes);
        interrumpir(productos);
    }

    private void interrumpir(ProcessMonitor proceso) {
        if (proceso != null && proceso.hilo != null && proceso.hilo.isAlive()) {
            proceso.hilo.interrupt();
        }
    }

    private static final class ProcessMonitor {
        private final String nombre;
        private final int total;
        private final long demoraMs;
        private final Label estado;
        private final Label porcentaje;
        private final ProgressBar progreso;
        private final Button iniciar;
        private final Button detener;
        private final Button reiniciar;
        private int numeroEjecucion;
        private Thread hilo;

        private ProcessMonitor(String nombre, int total, long demoraMs, Label estado,
                               Label porcentaje, ProgressBar progreso, Button iniciar,
                               Button detener, Button reiniciar) {
            this.nombre = nombre;
            this.total = total;
            this.demoraMs = demoraMs;
            this.estado = estado;
            this.porcentaje = porcentaje;
            this.progreso = progreso;
            this.iniciar = iniciar;
            this.detener = detener;
            this.reiniciar = reiniciar;
        }
    }
}
