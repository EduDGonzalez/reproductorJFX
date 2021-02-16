/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.poi.reproductormusica;

import java.io.File;
import java.net.URI;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

/**
 *
 * @author edudg
 */

public class Cancion {
    
    private final StringProperty cancion;
    private final StringProperty disco;
    private final StringProperty artista;
    private final ObjectProperty<File> archivo;
    private final ObjectProperty<Image> portada;
    private final ReadOnlyObjectWrapper<Media> medio;
    
    public Cancion(File file){
        disco = new SimpleStringProperty(this, "[Albun]");
        cancion = new SimpleStringProperty(this, "[Cancion]");
        artista = new SimpleStringProperty(this, "[Artista]");
        portada = new SimpleObjectProperty<>(this, "[Portada]");
        medio = new ReadOnlyObjectWrapper<>(this, "[reproducto]");
        archivo = new SimpleObjectProperty<>(this, "[Archivo]",file);
        actualizarMedio(file.toURI());
    }
    
    public String getCancion(){return cancion.get();}
    public void setCancion(String nombre){cancion.set(nombre);}
    public StringProperty cancionProperty(){return cancion;}
    
    public String getDisco(){return disco.get();}
    public void setDisco(String nombre){disco.set(nombre);}
    public StringProperty discoProperty(){return disco;}
    
    public String getArtista(){return artista.get();}
    public void setArtista(String nombre){artista.set(nombre);}
    public StringProperty artistaProperty(){return artista;}
    
    public Image getPortada(){return portada.get();}
    public void setPortada(Image imagen){portada.set(imagen);}
    public ObjectProperty<Image> portadaProperty(){return portada;}
    
    public File getArchivo(){return archivo.get();}
    public void setArchivo(File file){archivo.set(file);}
    public ObjectProperty<File> archivoProperty(){return archivo;}
    
    public Media getMedio(){return medio.get();}
    public ReadOnlyObjectWrapper medioProperty(){return medio;}
    

    private void actualizarMedio(URI toURI) {
        final Media medio;
        medio = new Media(archivo.getValue().toURI().toString());
        medio.getMetadata().addListener((MapChangeListener.Change<? extends String, ? extends Object> elemento) -> {
         if(elemento.wasAdded()){
            inicializaDatos(elemento.getKey(),elemento.getValueAdded());
        }
    });
    this.medio.setValue(medio);
    }

    private void inicializaDatos(String key, Object value) {
        switch(key){
            case "album": setDisco(value.toString());break;
            case "artist": setArtista(value.toString());break;
            case "title": setCancion(value.toString());break;
            case "image": setPortada((Image)value);break;
            default: break;
        }
    }
    
    
}
