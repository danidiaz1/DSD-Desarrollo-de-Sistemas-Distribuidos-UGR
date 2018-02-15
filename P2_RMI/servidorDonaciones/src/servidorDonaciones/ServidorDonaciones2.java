/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorDonaciones;

import donaciones.DonacionesSecundario;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * @brief Clase que instancia el servidor número 2 de donaciones (la segunda réplica).
 * @author Daniel Díaz Pareja
 */
public class ServidorDonaciones2 {
    public static void main(String[] args) {
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {
            String nombre_servidor = "misDonaciones2", nombre_replica = "misDonaciones1";
            DonacionesSecundario misDonaciones2 = new DonacionesSecundario(nombre_servidor,nombre_replica);
            Naming.rebind(nombre_servidor, misDonaciones2);
            System.out.println("Servidor " +nombre_servidor+ " preparado.");
        } catch (RemoteException | MalformedURLException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
