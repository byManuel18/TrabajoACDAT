package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Spotify2.Spotify.Ejecutable;
import enums.Escenas;
import javafx.fxml.FXML;

public class RegistryController extends GeneralController{

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	private void Return(){
		Ejecutable.CambiarEscena(600, 400, Escenas.INICIO);
		Ejecutable.CambiarTitulo("INICIO DE SESIÓN");
	}

}
