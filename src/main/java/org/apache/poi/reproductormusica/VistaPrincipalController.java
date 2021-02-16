/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.poi.reproductormusica;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 *
 * @author edudg
 */
public class VistaPrincipalController implements Initializable {

    private Cancion cancion;
    private MediaPlayer reproductor;

    @FXML
    private ImageView imagenPortada;
    @FXML
    private Label labelTitulo;
    @FXML
    private Label labelAutor;
    
    @FXML
    private Label duracionActual;

    @FXML
    private Slider sliderTiempo;

    @FXML
    private Label duracionTotal;

    @FXML
    private Label labelAbum;
   
    @FXML
    private Button botonAtras;
    
    @FXML
    private Button botonPlay;
    
    @FXML
    private Button botonAlante;
   


    @FXML
    void pulsadorPlay() throws IOException {
        if (botonPlay.getText().equals(">")) {
            reproductor.play();
            botonPlay.setText("| |");

        } else {
            reproductor.pause();
            botonPlay.setText(">");
        }
    }

    @FXML
    void pulsadoAtras() throws IOException {
        reproductor.seek(Duration.ZERO);
    }

    @FXML
    void pulsarAlante() throws IOException {
        reproductor.seek(reproductor.getTotalDuration());
    }

    public VistaPrincipalController() {
        
    }

    public VistaPrincipalController(Cancion cancion) {
        this.cancion = cancion;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (cancion != null) {
            presentarMedio();
        }
        duracionTotal.setText("--:--");
        duracionActual.setText("--:--");
        sliderTiempo.setMin(0.0);
        sliderTiempo.setMax(1.0);
        duracionActual.setId("duracion");
        duracionTotal.setId("duracion");
        botonPlay.setId("botonPlay");
    }

    private void presentarMedio() {
        labelTitulo.textProperty().bind(cancion.cancionProperty());
        labelAutor.textProperty().bind(cancion.artistaProperty());
        labelAbum.textProperty().bind(cancion.discoProperty());
        imagenPortada.imageProperty().bind(cancion.portadaProperty());
        reproductor = new MediaPlayer(cancion.getMedio());
        reproductor.currentTimeProperty().addListener((o) -> {
            Platform.runLater(()->{
                 sliderTiempo.setValue(reproductor.getCurrentTime().toMinutes() / reproductor.getTotalDuration().toMinutes());
                  int total = (int)reproductor.getTotalDuration().toSeconds();
                  duracionTotal.setText(total/60+":"+total%60);
                int actual = (int) reproductor.getCurrentTime().toSeconds();
                int mod2 = actual % 60;
                int new_val = (int) reproductor.getCurrentTime().toMinutes();
                if (mod2 < 10) {
                    duracionActual.setText(String.format("%d:0%d", new_val, mod2));
                } else if (mod2 >= 10) {
                    duracionActual.setText(String.format("%d:%d", new_val, mod2));
                }
            });
        });

        sliderTiempo.valueChangingProperty().addListener((ObservableValue<? extends Boolean> ob, Boolean valorAnterior, Boolean valorNuevo) -> {
            if (valorAnterior && !valorNuevo) {
                reproductor.seek(reproductor.getTotalDuration().multiply(sliderTiempo.getValue()));
                
            }
        });
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
        presentarMedio();
    }
    
}
