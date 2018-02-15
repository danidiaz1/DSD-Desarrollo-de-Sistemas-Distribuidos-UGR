/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorDonaciones;

import donaciones.Donaciones;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @brief Clase que instancia el servidor número 1 de donaciones (la primera réplica).
 *        Además, es el que debe lanzarse primero, ya que crea el registro de RMI.
 * @author Daniel Díaz Pareja
 */
public class ServidorDonaciones1 {
    public static void main(String[] args) {
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {
            String nombre_servidor = "misDonaciones1", nombre_replica = "misDonaciones2";
            Registry reg=LocateRegistry.createRegistry(1099);
            Donaciones misDonaciones1 = new Donaciones(nombre_servidor,nombre_replica);
            Naming.rebind(nombre_servidor, misDonaciones1);
            System.out.println("Servidor " + nombre_servidor + " preparado.");
        } catch (RemoteException | MalformedURLException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
