package Spotify2.Spotify;

import java.io.IOException;

import enums.Escenas;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Ejecutable extends Application{
	private static Scene scene;
	private static Stage stge;
	@Override
	public void start(Stage sta) throws Exception {
		try {
			scene = new Scene(loadFXML(Escenas.INICIO.getUrl()), 550, 300);
			stge=sta;
    		stge.setTitle("INICIO DE SESIÓN");
    		stge.setResizable(false);
    		stge.setScene(scene);
            //stage.setResizable(false);
            stge.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Ejecutable.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
	public static void main(String[] args) {
		launch(args);
	}



}
