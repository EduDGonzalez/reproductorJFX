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
import javafx.scene.control.ListView;
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
    private AnchorPane boxReproductor;

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
    private Button botonVolver;

    @FXML
    private ListView lista;

    @FXML
    private VistaPrincipalController vistaPrincipalController;

    boolean mostrarMenu = true;
    boolean mostrarRepro = true;
    private TranslateTransition animacionMenu;
    private TranslateTransition animacionReproductor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        boxReproductor.setTranslateX(root.getPrefWidth());
        menuHBox.setTranslateX(-menu.getPrefWidth());
        animacionMenu = new TranslateTransition(Duration.seconds(.20), menuHBox);
        animacionReproductor = new TranslateTransition(Duration.seconds(.5), boxReproductor);
        lista.setOnMouseClicked((t) -> {
            vistaPrincipalController.setCancion((Cancion) lista.getSelectionModel().getSelectedItem(), lista.getSelectionModel().getSelectedIndex());
            mostrarRepro();
        });
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
    void volverLista(ActionEvent event) throws IOException {
        mostrarRepro();
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
        lista.setItems(olist);
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

    void mostrarRepro() {
        if (mostrarRepro) {
            animacionReproductor.setFromX(root.getPrefWidth());
            animacionReproductor.setToX(0);
            mostrarRepro=false;
        }else{
            animacionReproductor.setFromX(0);
            animacionReproductor.setToX(root.getPrefWidth());
            mostrarRepro=true;
        }
        animacionReproductor.play();
    }

    void ocultarRepro() {
        animacionReproductor.setFromX(0);
        animacionReproductor.setToX(root.getPrefWidth());
        animacionReproductor.play();
    }
}
