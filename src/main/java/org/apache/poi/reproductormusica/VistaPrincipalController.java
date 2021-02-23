/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.poi.reproductormusica;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 *
 * @author edudg
 */
public class VistaPrincipalController implements Initializable {

    private static int cancionActual = 0;
    private Cancion cancion;
    private MediaPlayer reproductor;
    private List<Cancion> listaReproduccion;

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
        if (cancionActual == 0) {
        reproductor.seek(Duration.ZERO);
        } else {
            reproductor.stop();
            presentarMedio(listaReproduccion.get(cancionActual - 1));
            cancionActual--;
        }
    }

    @FXML
    void pulsarAlante() throws IOException {
        if (cancionActual == listaReproduccion.size()-1) {
        reproductor.seek(reproductor.getTotalDuration());
        } else {
            reproductor.stop();
            presentarMedio(listaReproduccion.get(cancionActual + 1));
            cancionActual++;
        }
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
        reproductor.play();
        botonPlay.setText("| |");
        
        //MI CODIGO
        
        reproductor.currentTimeProperty().addListener((o) -> {
            Platform.runLater(() -> {
                sliderTiempo.setValue(reproductor.getCurrentTime().toMinutes() / reproductor.getTotalDuration().toMinutes());
                int total = (int) reproductor.getTotalDuration().toSeconds();
                duracionTotal.setText(total / 60 + ":" + total % 60);
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

    private void presentarMedio(Cancion cancion) {
        labelTitulo.textProperty().bind(cancion.cancionProperty());
        labelAutor.textProperty().bind(cancion.artistaProperty());
        labelAbum.textProperty().bind(cancion.discoProperty());
        imagenPortada.imageProperty().bind(cancion.portadaProperty());
        reproductor = new MediaPlayer(cancion.getMedio());
        reproductor.play();
        botonPlay.setText("| |");
        reproductor.currentTimeProperty().addListener((o) -> {
            Platform.runLater(() -> {
                sliderTiempo.setValue(reproductor.getCurrentTime().toMinutes() / reproductor.getTotalDuration().toMinutes());
                int total = (int) reproductor.getTotalDuration().toSeconds();
                duracionTotal.setText(total / 60 + ":" + total % 60);
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
        if(reproductor ==null){
            this.cancion = cancion;
            presentarMedio(this.cancion);
        }else{
            reproductor.stop();
            this.cancion = cancion;
            presentarMedio(this.cancion);
        }
    }
    public void setCancion(Cancion cancion,int index) {
        if(reproductor ==null){
            this.cancion = cancion;
            presentarMedio(this.cancion);
        }else{
            reproductor.stop();
            this.cancion = cancion;
            presentarMedio(this.cancion);
        }
        this.cancionActual  = index;
    }

    public List<Cancion> getListaReproduccion() {
        return listaReproduccion;
    }

    public void setListaReproduccion(List<Cancion> listaReproduccion) {
        this.listaReproduccion = listaReproduccion;
    }

}
