/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idonaciones;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @brief Interfaz que deben implementar los servidores de cara al cliente.
 *        Contiene las operaciones necesarias para actuar como servidor de 
 *        donaciones: registrar cliente, identificarlo, realizar donación y devolver
 *        total.
 * @author Daniel Díaz Pareja
 */
public interface I_donaciones_servidor extends Remote {
    /**
     * @brief Intenta realizar un registro en el servidor. Este método se encarga de comprobar
     *        si el cliente no existe en ninguno de los servidores, en cuyo caso
     *        lo registra en el servidor con menor número de clientes.  
     * @param cliente nombre del cliente a registrar
     * @param password contraseña del cliente a registrar
     * @return 0 si el cliente se ha registrado
     *         1 si no se ha podido registrar (el cliente ya existía)
     * @throws RemoteException fallo en las comunicaciones
     */
    int registro(String cliente, String password) throws RemoteException;
    
    /** @brief Realiza una donacion de un cliente. El servidor del cliente se selecciona
     *         previamente en el código del cliente, gracias al método "identificarCliente",
     *         que devuelve el nombre del servidor donde está registrado el cliente.
     * @param cliente nombre del cliente que realiza la donacion (se supone previamente identificado)
     * @param cantidad cantidad a donar (>0)
     * @throws RemoteException fallo en las comunicaciones
     */
    void deposito(String cliente,float cantidad) throws RemoteException;
    
    /**
     * @brief Consulta la cantidad total donada a los servidores. El servidor del cliente 
     *        se selecciona  previamente en el código del cliente, gracias al método 
     *        "identificarCliente", que devuelve el nombre del servidor donde está registrado 
     *        el cliente.
     *              
     * @param cliente nombre del cliente que realiza la donacion (se supone previamente identificado)
     * @return !=- si se ha realizado la consulta
     *         -1 si no se ha podido realizar la consulta porque el cliente
     *           no ha hecho donaciones
     * @throws RemoteException fallo en las comunicaciones
     */
    float consultarTotal(String cliente) throws RemoteException;
    
    /**
     * @brief Se encarga de comprobar que el usuario y la contraseña existen y coinciden.
     *        Además, devuelve el nombre del servidor en el que el cliente está registrado,
     *        para que, desde que se identifica, se comunique siempre con dicho servidor.
     * @param cliente nombre del cliente a identificar
     * @param password contraseña del cliente
     * @return cadena != vacía (con el nombre del servidor donde el usuario está registrado)
     *         si se el usuario existe y su contraseña coincide
     *         cadena vacía ("") si el usuario no existe o su contraseña no coincide
     * @throws RemoteException fallo en las comunicaciones
     */
    String identificarUsuario(String cliente, String password) throws RemoteException;
    
    /**
     * @brief Consulta el total donado por un cliente concreto.
     * @param cliente cliente que consulta su cantidad total donada
     * @return cantidad total donada por el cliente en cuestión
     * @throws RemoteException fallo en las comunicaciones
     */
    float obtenerTotalDonadoDelCliente(String cliente) throws RemoteException;
    
    /**
     * @brief Consulta el número de donaciones de un cliente concreto.
     * @param cliente cliente que consulta su total de donaciones
     * @return total de donaciones del cliente en cuestión
     * @throws RemoteException fallo en las comunicaciones
     */
    public int obtenerNumeroDonaciones(String cliente) throws RemoteException;
}
