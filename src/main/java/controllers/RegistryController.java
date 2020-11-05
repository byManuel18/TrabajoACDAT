package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Spotify2.Spotify.Ejecutable;
import enums.Escenas;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RegistryController extends GeneralController{
	@FXML
	private ImageView image_;
	@FXML
	private TextField url_;
	@FXML
	private TextField name_;
	@FXML
	private TextField mail_;
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	private void Return(){
		Ejecutable.CambiarEscena(600, 400, Escenas.INICIO);
		Ejecutable.CambiarTitulo("INICIO DE SESIÓN");
	}
	@FXML
	private void EnterImage(){
		if(url_.getText().length()>0){
			image_.setImage(new Image(url_.getText()));
		}
	}
	@FXML
	private void Clear(){
		url_.clear();
		mail_.clear();
		name_.clear();
		image_.setImage(null);
	}

}
