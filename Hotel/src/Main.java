import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/vista.fxml"));
        stage.setTitle("HotelApp");
        stage.setScene(new Scene(root, 900, 560));
        stage.setMinWidth(850);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
