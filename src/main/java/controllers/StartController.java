package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import DAOS.UsersDAO;
import Spotify2.Spotify.Ejecutable;
import enums.Escenas;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.User;

public class StartController extends GeneralController{

	@FXML
	private TextField email_;

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	private void Registry(){
		Ejecutable.CambiarEscena(600, 400, Escenas.REGISTRY);
		Ejecutable.CambiarTitulo("Registry");
	}
	@FXML
	private void GetIn(){
		if(email_.getText().length()>0){
			User u=new UsersDAO(email_.getText());
			if(u.getId()>0){

				if(u.isActive()){

				}else{

				}

			}else{

			}
		}

	}

}
