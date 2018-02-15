# DSD Desarrollo de Sistemas Distribuidos

Prácticas de Desarrollo de Sistemas Distribuidos (DSD), Grado en Ingeniería Informática, UGR 2016-2017

## Descripción de las prácticas

## Práctica 1: Programación de un DNS con RPC (Remote Procedure Call)

![alt text](https://github.com/danidiaz1/DSD-Desarrollo-de-Sistemas-Distribuidos-UGR/blob/master/P1_RPC/doc/figura1.png "Figura 1")

Esta práctica consiste en, utilizando rpcgen, programar una simplificación de un servicio DNS, el cual es un servicio jerárquico organizado en base a subredes para resolver la obtención de la dirección IP de un equipo a partir de su nombre, independientemente de la subred donde se encuentre el equipo.

Cada subred dispone de un servidor DNS que consiste en un servicio básico de registro de nombres de equipos y sus correspondientes IPs, los equipos pertenecen a dicha subred (véase la figura anterior). En el caso de subredes situadas en los nodos hoja de la jerarquía, dicho servidor conoce y registra la dirección del servidor superior en la jerarquía al que tiene que consultar en caso de que no pueda resolver la obtención de la IP de un equipo que no pertenezca a su subred. En el caso de que una subred sea intermedia en la jerarquía, además de la dirección del servidor superior en la jerarquía, dicho servidor también conoce y registra las direcciones de los servidores de cada una de las subredes que son nodos inferiores en la jerarquía.

Ello permite que una petición recibida desde un servidor inferior en la jerarquía sea propagada al servidor de la subred que corresponde al equipo buscado. Un equipo se ha de nombrar de forma que se especifique nombre de equipo y subred a la que pertenece (e.g. equipo21.red2). Un registro consiste en el nombre del equipo junto con su correspondiente IP (ambos campos serán cadenas de caracteres). El servidor DNS tendrá que implementar operaciones de alta y baja de registros de equipos que pertenecen a su subred, y obtención de IPs de equipos dados sus nombres ya sean pertenecientes a su subred o no. 

Se tiene en cuenta que cuando un servidor DNS tiene que resolver la obtención de IP de un equipo que no pertenece a su subred, el mismo servidor es el responsable de transmitir petición al DNS siguiente en la jerarquía, y así sucesivamente hasta que la petición alcance al DNS que mantiene la información. 

Cada servidor DNS también tendrá registradas de la forma más conveniente las direcciones de los servidores DNS con los que conecta directamente en la jerarquía.

#### Instrucciones.

Compilar:

- Cliente: make -f cliente.mak
- DNS2: make -f Makefile.dns2
- DNS1: make -f Makefile.dns

Funcionamiento: 

Poner en marcha los ejecutables de los servidores y usar el cliente de la siguiente manera:

- Para REGISTRAR un equipo, usar: ./client [direccion] registrar [nombre_dns_sin_subred] [ip]
- Para ELIMINAR un equipo, usar: ./client [direccion] borrar [nombre_dns_con_subred]
- Para RESOLVER un equipo, usar: ./client [direccion] resolver [nombre_dns_con_subred]
