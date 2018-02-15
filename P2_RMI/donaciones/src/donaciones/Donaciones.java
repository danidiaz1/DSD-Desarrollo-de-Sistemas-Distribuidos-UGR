/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package donaciones;

import idonaciones.I_donaciones_replica;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import idonaciones.I_donaciones_servidor;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @brief Clase que modela la funcionalidad de los servidores de donaciones
 * @author Daniel Díaz Pareja
 */
public class Donaciones extends UnicastRemoteObject implements I_donaciones_servidor,
        I_donaciones_replica{
    
    private Map<String, Cliente> clientes;  // Mapa que contiene a los clientes. 
           //Está formado por un par <String, Cliente>
        // donde el String es el nombre del cliente y el entero es una clase que encapsula
        // los datos que queremos guardar del cliente
    
    private float subtotal; // subtotal donado a este servidor
    private String replica; // Nombre del objeto servidor réplica de donaciones
    private String nombre; // Nombre en la red del objeto.
    public Donaciones(String nombre, String replica) throws RemoteException {
        clientes = new HashMap();
        subtotal = 0;
        this.replica = replica;
        this.nombre = nombre;
    }
    
    /**************** Métodos de conexión con el cliente **********************/
    
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
    @Override
    public int registro(String cliente, String password) throws RemoteException {
        int cod_error = 1;
        
        boolean existe_cliente_aqui = this.existeCliente(cliente);
        
        if (!existe_cliente_aqui){
            I_donaciones_replica la_replica = this.getReplica("localhost", this.replica);
            boolean existe_cliente_replica = la_replica.existeCliente(cliente);
            
            if (!existe_cliente_replica){
                int num_clientes_aqui = this.getNumeroClientes();
                int num_clientes_replica = la_replica.getNumeroClientes();
                
                if (num_clientes_aqui <= num_clientes_replica)
                    this.confirmarRegistro(cliente, password, this.nombre);
                else
                    la_replica.confirmarRegistro(cliente, password, la_replica.getNombre());
                
                cod_error = 0;
            }
        }
        
        return cod_error;
    }

    /** @brief Realiza una donacion de un cliente. El servidor del cliente se selecciona
     *         previamente en el código del cliente, gracias al método "identificarCliente",
     *         que devuelve el nombre del servidor donde está registrado el cliente.
     * @param cliente nombre del cliente que realiza la donacion (se supone previamente identificado)
     * @param cantidad cantidad a donar (>0)
     * @throws RemoteException fallo en las comunicaciones
     */
    @Override
    public void deposito(String cliente, float cantidad) throws RemoteException {
        Cliente client = clientes.get(cliente);
        this.incrementarSubtotal(cantidad);
        System.out.println("Donación de " +cantidad+ " del cliente "+cliente +".");
        client.incrementarNumDonaciones();
        client.incrementarTotalDonado(cantidad);
    } 

    /**
     * @brief Consulta la cantidad total donada a los servidores. El servidor del cliente 
     *        se selecciona  previamente en el código del cliente, gracias al método 
     *        "identificarCliente", que devuelve el nombre del servidor donde está registrado 
     *        el cliente.
     *              
     * @param cliente nombre del cliente que consulta el total (se supone previamente identificado)
     * @return !=- si se ha realizado la consulta
     *         -1 si no se ha podido realizar la consulta porque el cliente
     *           no ha hecho donaciones
     * @throws RemoteException fallo en las comunicaciones
     */
    @Override
    public float consultarTotal(String cliente) throws RemoteException {
        float valor_retorno = -1;

        int cantidad_donaciones = clientes.get(cliente).getNumero_donaciones();
        System.out.println("Consultando el total de donaciones. El cliente que consulta es: "+cliente+" , y tiene "
        + clientes.get(cliente).getNumero_donaciones()+" donaciones.");
        if (cantidad_donaciones > 0)
            valor_retorno = this.subtotal;

        return valor_retorno;
    }
    
    /**
     * @brief Se encarga de comprobar que el usuario y la contraseña existen y coinciden.
     *        Además, devuelve el nombre del servidor en el que el cliente está registrado,
     *        para que, desde que se identifica, se comunique siempre con dicho servidor.
     * @param cliente nombre del cliente a identificar
     * @param password contraseña del cliente
     * @return cadena != vacía (con el nombre del servidor donde el usuario está registrado)
     *         si se el usuario existe y su contraseña coincide
     *         cadena vacía ("") si el usuario no existe o su contraseña no coincide
     * @throws RemoteException 
     */
    @Override
    public String identificarUsuario(String cliente, String password) throws RemoteException{
        String servidor = "";
        boolean identificado;
        boolean existe_cliente_aqui = this.existeCliente(cliente);
        
        if (!existe_cliente_aqui){
            I_donaciones_replica la_replica = this.getReplica("localhost", this.replica);
            boolean existe_cliente_replica = la_replica.existeCliente(cliente); 
            
            if (existe_cliente_replica){
                identificado = la_replica.confirmarIdentificacion(cliente, password);
                if (identificado)
                    servidor = this.replica;
            }
        } else {
            identificado = this.confirmarIdentificacion(cliente, password);
            if (identificado)
                servidor = this.nombre;
        }
        return servidor;
    }
    
    /**
     * @brief Consulta el total donado por un cliente concreto.
     * @param cliente cliente que consulta su cantidad total donada
     * @return cantidad total donada por el cliente en cuestión
     * @throws RemoteException 
     */
    @Override
    public float obtenerTotalDonadoDelCliente(String cliente) throws RemoteException{
        Cliente client = clientes.get(cliente);
        return client.getTotal_donado();
    }
    
    /**
     * @brief Consulta el número de donaciones de un cliente concreto.
     * @param cliente cliente que consulta su total de donaciones
     * @return total de donaciones del cliente en cuestión
     * @throws RemoteException fallo en las comunicaciones
     */
    @Override
    public int obtenerNumeroDonaciones(String cliente) throws RemoteException{
        Cliente client = clientes.get(cliente);
        return client.getNumero_donaciones();
    }
    
    /*************** Métodos de conexión entre servidores *********************/
    /**
     * @brief Realiza el registro directamente en el servidor, sin comprobaciones.
     * @param cliente nombre del cliente
     * @param password contraseña del cliente
     * @param nombre nombre en la red del objeto servidor
     * @throws RemoteException fallo en las comunicaciones
     */
    @Override
    public void confirmarRegistro(String cliente, String password,String nombre) throws RemoteException {
        
        clientes.put(cliente,new Cliente(cliente,password));
        System.out.println("Nuevo cliente: "+cliente);
        System.out.println("Clientes de este servidor:\n" +this.clientes.keySet());
    }
    
    /**
     * @brief consulta si existe o no un cliente en el servidor
     * @param cliente nombre del cliente
     * @return true si el cliente existe
     *         false si el cliente no existe
     * @throws java.rmi.RemoteException fallo en las comunicaciones
     */
    @Override
    public boolean existeCliente(String cliente) throws RemoteException{
        return clientes.containsKey(cliente);
    }
    
    /**
     * @brief método para obtener el número de clientes registrados en el servidor
     * @return número de clientes registrados en esta réplica
     * @throws RemoteException fallo en las comunicaciones
     */
    @Override
    public int getNumeroClientes() throws RemoteException {
        return clientes.size();
    }

    /**
     * @brief método para obtener el subtotal de donaciones del servidor
     * @return subtotal de donaciones realizada en esta réplica
     * @throws RemoteException fallo en las comunicaciones
     */
    @Override
    public float getSubtotal() throws RemoteException {
        return this.subtotal;
    }
    
    /**
     * @brief obtiene la réplica del servidor dado el host donde está la réplica
     *        y el nombre de la réplica
     * @param host nombre del host donde está la réplica
     * @param nombre nombre de la réplica a obtener
     * @return referencia a la réplica del servidor
     * @throws RemoteException fallo en las comunicaciones
     */
    @Override
    public I_donaciones_replica getReplica(String host, String nombre) throws RemoteException {
        I_donaciones_replica la_replica = null;
        
        try {
            Registry mireg = LocateRegistry.getRegistry(host, 1099);
            la_replica = (I_donaciones_replica)mireg.lookup(nombre);
        } catch(NotBoundException | RemoteException e) {
            System.err.println("Exception del sistema: " + e);
        }
        
        return la_replica;
    }
    
    /**
     * @brief Método para consultar el nombre del objeto servidor en la red (en el registry)
     * @return nombre del objeto servidor en la red
     * @throws RemoteException fallo en las comunicaciones
     */
    @Override
    public String getNombre() throws RemoteException {
        return this.nombre;
    }
    
    /**
     * @brief Incrementa el subtotal del servidor
     * @param cantidad cantidad a incrementar
     * @throws RemoteException fallo en las comunicaciones
     */
    @Override
    public void incrementarSubtotal(float cantidad) throws RemoteException {
        this.subtotal += cantidad;
    }
    
    /**
     * @brief método entre servidores para saber si un usuario y contraseña dadas
     *        coinciden, es decir, para identificar al usuario
     * @param cliente nombre de registro cliente
     * @param password contraseña del cliente
     * @return true si el cliente y su contraseña asociaada coinciden
     *         false en caso contrario
     * @throws RemoteException 
     */
    @Override
    public boolean confirmarIdentificacion(String cliente, String password) throws RemoteException{
        System.out.println("Intentando identificar al cliente " + cliente +".");
        String pass = clientes.get(cliente).getPassword();
        System.out.println("Resultado = " + pass.equals(password));
        return pass.equals(password);
    }
    
    /* Utilizados para hacer pruebas o finalmente descartados*/
    /*@Override
    public void asignarReplica(String host, String replica) throws RemoteException {
        try {
            Registry mireg = LocateRegistry.getRegistry(host, 1099);
            this.replica = (I_donaciones_servidor)mireg.lookup(replica);
        } catch(NotBoundException | RemoteException e) {
                System.err.println("Exception del sistema: " + e);
        }
    }*/
    /*@Override
    public ArrayList<String> mostrarUsuariosReplica()throws RemoteException{

        return replica.mostrarUsuarios();
    }
    
    @Override
    public ArrayList<String> mostrarUsuarios() throws RemoteException{

        return new ArrayList(this.clientes.keySet());
        
    }*/  
}
