package com.dahjana.contactos;

/**
 * Created by Dahjana on 04/05/2015.
 */
import android.net.Uri;

public class Contact {

    private String _nombre, _telefono, _email, _direccion;
    private Uri _imageURI;


    public Contact(String nombre, String telefono, String email, String direccion, Uri imageURI) {
        _nombre = nombre;
        _telefono = telefono;
        _email = email;
        _direccion = direccion;
        _imageURI = imageURI;

    }


    public String getNombre() {
        return _nombre;
    }

    public String getTelefono() {
        return _telefono;
    }

    public String getEmail() {
        return _email;
    }

    public String getDireccion() {
        return _direccion;
    }

    public Uri getImageURI() { return _imageURI; }


}