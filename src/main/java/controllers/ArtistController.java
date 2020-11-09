package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import DAOS.ArtistDAO;
import Spotify2.Spotify.Ejecutable;
import enums.Escenas;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Artist;

public class ArtistController extends GeneralController{
	@FXML
	private TableView<Artist> artisttamble;
	@FXML
	private javafx.scene.control.TableColumn<Artist, ImageView> columna_image;
	@FXML
	private javafx.scene.control.TableColumn<Artist, String> columna_name;
	@FXML
	private javafx.scene.control.TableColumn<Artist,String> columna_nacionalidad;
	private ObservableList<Artist> artistlist_;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		artistlist_=FXCollections.observableArrayList();
		ShowAll();

		this.columna_name.setCellValueFactory(eachRowData -> {
			return new SimpleObjectProperty<>(eachRowData.getValue().getName());
		});
		this.columna_nacionalidad.setCellValueFactory(eachRowData -> {
			return new SimpleObjectProperty<>(eachRowData.getValue().getNationality());
		});
		this.columna_image.setCellValueFactory(eachRowData -> {
			ImageView img=new ImageView(eachRowData.getValue().getPhoto());
			img.setFitHeight(50);
			img.setFitWidth(50);
			return new SimpleObjectProperty<>(img);
		});


		artisttamble.setItems(artistlist_);

	}
	private void ShowAll(){
		artistlist_.clear();
		artistlist_.addAll(ArtistDAO.SelectAll());
	}
	@FXML
	public void Return(){
		Ejecutable.CambiarEscena(600, 400, Escenas.ADMINISTRATION);
		Ejecutable.CambiarTitulo("Administration");
	}

}
