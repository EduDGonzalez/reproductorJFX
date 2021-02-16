/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.poi.reproductormusica;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    private VistaPrincipalController vistaPrincipalController;
    
    boolean mostrar = true;
    private TranslateTransition animacionMenu;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
        menuHBox.setTranslateX(-menu.getPrefWidth());
        animacionMenu = new TranslateTransition(Duration.seconds(.20), menuHBox);

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
    void exitButton(ActionEvent event) throws IOException {
        Platform.exit();
    }

    @FXML
    void mostrarMenu(ActionEvent event) throws IOException {

        if (mostrar) {
            animacionMenu.setFromX(-menu.getPrefWidth());
            animacionMenu.setToX(0);
            botonMenu.setText("<");
            mostrar = false;
        }else{
            animacionMenu.setFromX(0);
            animacionMenu.setToX(-menu.getPrefWidth());
            botonMenu.setText(">");
            mostrar = true;
        }
        animacionMenu.play();
    }
}
