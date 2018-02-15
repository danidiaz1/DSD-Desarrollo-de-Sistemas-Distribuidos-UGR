/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package donaciones;

/**
 * @brief Clase que modela la información a guardar de un cliente.
 *        En la práctica no se pide, pero se ha incluido una contraseña y el
 *        total donado por el usuario en concreto.
 * @author Daniel Díaz Pareja
 */
public class Cliente {
    private String nombre;
    private String password;
    private int numero_donaciones;
    private float total_donado;


    Cliente(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
        this.numero_donaciones = 0;
        this.total_donado = 0;
    }

    String getNombre() {
        return nombre;
    }

    String getPassword() {
        return password;
    }

    int getNumero_donaciones() {
        return numero_donaciones;
    }

    float getTotal_donado() {
        return total_donado;
    }

    void setNumero_donaciones(int numero_donaciones) {
        this.numero_donaciones = numero_donaciones;
    }

    void setTotal_donado(float total_donado) {
        this.total_donado = total_donado;
    }

    void incrementarNumDonaciones() {
        this.numero_donaciones++;
    }
    
    void incrementarTotalDonado(float cantidad) {
        this.total_donado+=cantidad;
    }
    
    
}
