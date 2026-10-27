import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class Controlador {

    @FXML private Label lblHotel;
    @FXML private ListView<Habitacion> tabla;
    @FXML private TextField txtNumero;
    @FXML private Label lblResultado;

    private final ObservableList<Habitacion> habitaciones = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Cada fila se dibuja con el componente propio TarjetaHabitacion
        tabla.setCellFactory(lv -> new ListCell<>() {
            private final TarjetaHabitacion tarjeta = new TarjetaHabitacion();
            {
                setStyle("-fx-background-color: transparent; -fx-padding: 4 6 4 6;");
                tarjeta.setOnVerDetalles(h -> abrirDetalles(h));
                // La fila seleccionada (la que devuelve la búsqueda) se ve en gris claro
                selectedProperty().addListener((obs, antes, ahora) -> tarjeta.setResaltada(ahora));
            }

            @Override
            protected void updateItem(Habitacion h, boolean empty) {
                super.updateItem(h, empty);
                if (empty || h == null) {
                    setGraphic(null);
                } else {
                    tarjeta.setHabitacion(h);
                    tarjeta.setResaltada(isSelected());
                    setGraphic(tarjeta);
                }
            }
        });

        cargarXML();
        tabla.setItems(habitaciones);
    }

    /** Ventana emergente modal con toda la información de la habitación. */
    private void abrirDetalles(Habitacion h) {
        Stage ventana = new Stage();
        ventana.initOwner(tabla.getScene().getWindow());
        ventana.initModality(Modality.WINDOW_MODAL); // bloquea la ventana principal
        ventana.setTitle("Habitación " + h.getNumero());

        Label titulo = new Label("Habitación " + h.getNumero());
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        GridPane datos = new GridPane();
        datos.setHgap(16);
        datos.setVgap(8);
        String[][] filas = {
                {"Planta:", String.valueOf(h.getPlanta())},
                {"Tipo:", h.getTipo()},
                {"Capacidad:", h.getCapacidad() + " personas"},
                {"Precio/noche:", String.format("%.2f €", h.getPrecioNoche())},
                {"Vista:", h.getVista()},
                {"Accesible:", h.getAccesible()},
                {"Estado:", h.getEstado()}
        };
        for (int i = 0; i < filas.length; i++) {
            Label etiqueta = new Label(filas[i][0]);
            etiqueta.setStyle("-fx-font-weight: bold;");
            datos.add(etiqueta, 0, i);
            datos.add(new Label(filas[i][1]), 1, i);
        }

        Button cerrar = new Button("Cerrar");
        cerrar.setOnAction(e -> ventana.close());

        VBox raiz = new VBox(16, titulo, datos, cerrar);
        raiz.setPadding(new Insets(20));
        raiz.setAlignment(Pos.TOP_LEFT);

        ventana.setScene(new Scene(raiz, 320, 330));
        ventana.setResizable(false);
        ventana.showAndWait();
    }

    private static String texto(Element padre, String etiqueta) {
        return padre.getElementsByTagName(etiqueta).item(0).getTextContent().trim();
    }

    /** Lee reservas.xml (carpeta de recursos o carpeta del proyecto). */
    private void cargarXML() {
        try {
            InputStream in = getClass().getResourceAsStream("/xml/reservas.xml");
            if (in == null) in = new java.io.FileInputStream("src/xml/reservas.xml");
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);

            lblHotel.setText("HotelApp · " + doc.getElementsByTagName("nombre").item(0).getTextContent());

            NodeList nodos = doc.getElementsByTagName("habitacion");
            for (int i = 0; i < nodos.getLength(); i++) {
                Element e = (Element) nodos.item(i);
                habitaciones.add(new Habitacion(
                        Integer.parseInt(e.getAttribute("numero")),
                        Integer.parseInt(e.getAttribute("planta")),
                        texto(e, "tipo"),
                        Integer.parseInt(texto(e, "capacidad")),
                        Double.parseDouble(texto(e, "precioNoche")),
                        texto(e, "vista"),
                        texto(e, "accesible"),
                        e.getAttribute("estado")));
            }
        } catch (Exception ex) {
            lblResultado.setText("Error al leer reservas.xml: " + ex.getMessage());
        }
    }

    @FXML
    private void buscar() {
        int numero;
        try {
            numero = Integer.parseInt(txtNumero.getText().trim());
        } catch (NumberFormatException ex) {
            lblResultado.setText("Introduce un número de habitación válido.");
            return;
        }
        for (Habitacion h : habitaciones) {
            if (h.getNumero() == numero) {
                String msg;
                switch (h.getEstado()) {
                    case "disponible":    msg = "DISPONIBLE"; break;
                    case "ocupada":       msg = "OCUPADA"; break;
                    case "mantenimiento": msg = "en MANTENIMIENTO"; break;
                    default:              msg = "en estado desconocido";
                }
                lblResultado.setText("La habitación " + numero + " está " + msg + ".");
                tabla.getSelectionModel().select(h);
                tabla.scrollTo(h);
                return;
            }
        }
        lblResultado.setText("La habitación " + numero + " NO EXISTE.");
        tabla.getSelectionModel().clearSelection();
    }
}