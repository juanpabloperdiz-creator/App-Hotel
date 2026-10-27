import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

/**
 * Componente visual propio: tarjeta de una habitación.
 * Datos configurables: número, tipo, capacidad, precio y estado (vía setHabitacion).
 * Evento: botón "Ver detalles" (vía setOnVerDetalles).
 */
public class TarjetaHabitacion extends HBox {

    private final Label lblNumero = new Label();
    private final Label lblTipo = new Label();
    private final Label lblPrecio = new Label();
    private final Label lblCapacidad = new Label();
    private final Label lblEstado = new Label();
    private final Button btnDetalles = new Button("Ver detalles");

    private Habitacion habitacion;
    private Consumer<Habitacion> onVerDetalles;

    public TarjetaHabitacion() {
        lblNumero.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        lblTipo.setStyle("-fx-text-fill: #555;");
        lblPrecio.setStyle("-fx-text-fill: #555;");
        lblCapacidad.setStyle("-fx-text-fill: #555;");

        VBox datos = new VBox(2, lblNumero, lblTipo, lblPrecio, lblCapacidad);

        Region hueco = new Region();
        HBox.setHgrow(hueco, Priority.ALWAYS);

        VBox derecha = new VBox(10, lblEstado, btnDetalles);
        derecha.setAlignment(Pos.CENTER_RIGHT);

        btnDetalles.setStyle("-fx-background-color: #2f6fdb; -fx-text-fill: white;");
        btnDetalles.setOnAction(e -> {
            if (onVerDetalles != null && habitacion != null) onVerDetalles.accept(habitacion);
        });

        getChildren().addAll(datos, hueco, derecha);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10, 16, 10, 16));
        setResaltada(false);
    }

    /** Resalta la tarjeta en gris claro (por ejemplo, cuando se encuentra en la búsqueda). */
    public void setResaltada(boolean resaltada) {
        setStyle("-fx-background-color: " + (resaltada ? "#e2e2e2" : "white") + "; "
                + "-fx-background-radius: 10; -fx-border-color: #d5dbe6; -fx-border-radius: 10;");
    }

    public TarjetaHabitacion(Habitacion h) {
        this();
        setHabitacion(h);
    }

    /** Rellena la tarjeta con los datos de una habitación (se puede llamar varias veces). */
    public void setHabitacion(Habitacion h) {
        this.habitacion = h;
        lblNumero.setText("Habitación " + h.getNumero());
        lblTipo.setText("Tipo: " + h.getTipo());
        lblPrecio.setText(String.format("Precio: %.0f €", h.getPrecioNoche()));
        lblCapacidad.setText("Capacidad: " + h.getCapacidad() + (h.getCapacidad() == 1 ? " persona" : " personas"));

        String fondo, texto, nombre;
        switch (h.getEstado()) {
            case "disponible":    fondo = "#d6f5df"; texto = "#1b7a3a"; nombre = "Disponible"; break;
            case "ocupada":       fondo = "#fbdcdc"; texto = "#b42323"; nombre = "Ocupada"; break;
            case "mantenimiento": fondo = "#fdeccb"; texto = "#9a6a00"; nombre = "Mantenimiento"; break;
            default:              fondo = "#e6e6e6"; texto = "#555";    nombre = h.getEstado();
        }
        lblEstado.setText("● " + nombre);
        lblEstado.setStyle("-fx-background-color: " + fondo + "; -fx-text-fill: " + texto + "; "
                + "-fx-background-radius: 12; -fx-padding: 3 10 3 10; -fx-font-weight: bold;");
    }

    /** Qué ocurre al pulsar "Ver detalles". */
    public void setOnVerDetalles(Consumer<Habitacion> handler) {
        this.onVerDetalles = handler;
    }
}
