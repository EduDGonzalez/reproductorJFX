/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.poi.reproductormusica;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author edudg
 */
public class Vista_menuController implements Initializable {

    @FXML
    private VBox menu;

    @FXML
    private HBox menuHBox;

    @FXML
    private HBox listaHBox;

    @FXML
    private AnchorPane root;

    @FXML
    private Button botonAbrirCancion;

    @FXML
    private Button botonAbrirCarpeta;

    @FXML
    private Button botonOpciones;

    @FXML
    private Button botonSalir;

    @FXML
    private Button botonMenu;

    @FXML
    private Button botonLista;

    @FXML
    private VistaPrincipalController vistaPrincipalController;

    boolean mostrarMenu = true;
    boolean mostrarLista = true;
    private TranslateTransition animacionMenu;
    private TranslateTransition animacionLista;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        menuHBox.setTranslateX(-menu.getPrefWidth());
        animacionMenu = new TranslateTransition(Duration.seconds(.20), menuHBox);
        animacionLista = new TranslateTransition(Duration.seconds(.20), listaHBox);

    }

    @FXML
    void abrirCancion(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"));
        Cancion cancion = new Cancion(fileChooser.showOpenDialog(null));
        vistaPrincipalController.setCancion(cancion);
    }

    @FXML
    void abrirCarpeta(ActionEvent event) throws IOException {
        ObservableList<Cancion> olist = FXCollections.observableArrayList();
        DirectoryChooser fileChooser = new DirectoryChooser();
        fileChooser.setTitle("Open Resource File");
        File dir = fileChooser.showDialog(null);

        File[] listaReproduccion = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().toLowerCase().endsWith(".mp3");
            }
        });
        for (File file : listaReproduccion) {
            Cancion song = new Cancion(file);
            olist.add(song);
        }
        vistaPrincipalController.setListaReproduccion(olist);
    }

    @FXML
    void exitButton(ActionEvent event) throws IOException {
        Platform.exit();
    }

    @FXML
    void mostrarMenu(ActionEvent event) throws IOException {

        if (mostrarMenu) {
            animacionMenu.setFromX(-menu.getPrefWidth());
            animacionMenu.setToX(0);
            botonMenu.setText("<");
            mostrarMenu = false;
        } else {
            animacionMenu.setFromX(0);
            animacionMenu.setToX(-menu.getPrefWidth());
            botonMenu.setText(">");
            mostrarMenu = true;
        }
        animacionMenu.play();
    }

    @FXML
    void mostrarLista(ActionEvent event) throws IOException {
        if (mostrarLista) {
            animacionLista.setFromX(-menu.getPrefWidth());
            animacionLista.setToX(0);
            botonMenu.setText("<");
            mostrarLista = false;
        } else {
            animacionLista.setFromX(0);
            animacionLista.setToX(-menu.getPrefWidth());
            botonMenu.setText(">");
            mostrarLista = true;
        }
        animacionMenu.play();
    }
}
