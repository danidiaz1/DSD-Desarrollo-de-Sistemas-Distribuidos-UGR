/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idonaciones;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @brief Interfaz que contiene las operaciones necesarias para que un servidor
 *        actúe como réplica, es decir, para que actúe como cliente de otro
 *        servidor. Debe tener métodos para devolver datos que necesite el otro servidor:
 *        obtener una referencia a la propia réplica, comprobar si existe cierto
 *        cliente registrado, devoler el número de clientes registrados...
 *        
 * @author Daniel Díaz Pareja
 */
public interface I_donaciones_replica extends Remote {
    /**
     * @brief consulta si existe o no un cliente en el servidor
     * @param cliente nombre del cliente
     * @return true si el cliente existe
     *         false si el cliente no existe
     * @throws java.rmi.RemoteException fallo en las comunicaciones
     */
    boolean existeCliente(String cliente) throws RemoteException;
    
    /**
     * @brief método para obtener el número de clientes registrados en el servidor
     * @return número de clientes registrados en esta réplica
     * @throws RemoteException fallo en las comunicaciones
     */
    int getNumeroClientes() throws RemoteException;
    
    /**
     * @brief método para obtener el subtotal de donaciones del servidor
     * @return subtotal de donaciones realizada en esta réplica
     * @throws RemoteException fallo en las comunicaciones
     */
    float getSubtotal() throws RemoteException;
    
    /**
     * @brief Realiza el registro directamente en el servidor, sin comprobaciones.
     * @param cliente nombre del cliente
     * @param password contraseña del cliente
     * @param nombre nombre en la red del objeto servidor
     * @throws RemoteException fallo en las comunicaciones
     */
    public void confirmarRegistro(String cliente, String password, String nombre) throws RemoteException;
    
    /**
     * @brief obtiene la réplica del servidor dado el host donde está la réplica
     *        y el nombre de la réplica
     * @param host nombre del host donde está la réplica
     * @param nombre nombre de la réplica a obtener
     * @return referencia a la réplica del servidor
     * @throws RemoteException fallo en las comunicaciones
     */
    public I_donaciones_replica getReplica(String host, String nombre) throws RemoteException;
    
    /**
     * @brief Método para consultar el nombre del objeto servidor en la red (en el registry)
     * @return nombre del objeto servidor en la red
     * @throws RemoteException fallo en las comunicaciones
     */
    public String getNombre() throws RemoteException;
    
    /**
     * @brief Incrementa el subtotal del servidor
     * @param cantidad cantidad a incrementar
     * @throws RemoteException fallo en las comunicaciones
     */
    public void incrementarSubtotal(float cantidad) throws RemoteException;
    
    /**
     * @brief método entre servidores para saber si un usuario y contraseña dadas
     *        coinciden
     * @param cliente nombre de registro cliente
     * @param password contraseña del cliente
     * @return true si el cliente y su contraseña asociaada coinciden
     *         false en caso contrario
     * @throws RemoteException 
     */
    public boolean confirmarIdentificacion(String cliente, String password) throws RemoteException;
    
    /* Utilizados para hacer pruebas o finalmente descartados: */
    
    /**
     * @brief Asigna al servidor de donaciones su réplica
     * @precondition el cliente debe existir en el servidor
     * @param replica nombre de la replica a asignar
     * @param host host donde está la réplica
     * @throws RemoteException fallo en las comunicaciones
     */
    //void asignarReplica(String host, String replica) throws RemoteException;
    /*ArrayList<String> mostrarUsuariosReplica() throws RemoteException;
    
    ArrayList<String> mostrarUsuarios() throws RemoteException;*/
}
