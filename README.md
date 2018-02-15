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


## Práctica 2: Servidor de donaciones con RMI

El ejercicio consiste en desarrollar en RMI un sistema cliente-servidor teniendo en cuenta los siguientes requisitos:

1. El servidor será un servidor replicado (con exactamente 2 replicas), cada replica desplegada en una máquina diferente, y estará encargado de recibir donaciones de entidades (clientes) para una causa humanitaria.

2. El servidor proporcionará dos operaciones, registro de una entidad interesada (cliente) en la causa, y depósito de una donación a la causa. No es posible realizar un depósito (o más) sin haberse registrado el cliente previamente.

3. Cuando una entidad desea registrarse y contacta con cualquiera de las dos réplicas del servidor, entonces el registro del cliente debe ocurrir realmente y de forma transparente en la réplica con menos entidades registradas. Es decir, el cliente sólo se ha dirigido a una réplica aunque esta no haya sido donde realmente ha quedado registrado, pero a partir de ese momento, el cliente realizará los depósitos en la réplica del servidor donde ha sido registrado.

4. Cada réplica del servidor mantendrá el subtotal de las donaciones realizadas en dicha replica.

5. Un cliente no podrá registrarse más de una vez, ni siquiera en replicas distintas.

6. Los servidores también ofrecerán una operación de consulta del total donado en un momento dado. Dicha operación sólo podrá llevarse a cabo si el cliente previamente se ha registrado y ha realizado al menos un depósito. Cuando un cliente consulte la cantidad total donada hasta el momento, sólo hará la petición a la réplica donde se encuentra registrado, y ésta será la encargada, realizando la operación oportuna con la otra replica, de devolver el total donado hasta el momento.

## Práctica 3: Sistema domótico con Node.js, socketIO y mongoDB

![alt text](https://github.com/danidiaz1/DSD-Desarrollo-de-Sistemas-Distribuidos-UGR/blob/master/P3_Nodejs/doc/figura1.png "Figura 1")

Se supone un sistema domótico básico compuesto de dos sensores (luminosidad y temperatura), dos actuadores (motor persiana y sistema de Aire/Acondicionado), un servidor que sirve páginas para mostrar el estado y actuar sobre los elementos de la vivienda (véase la figura anterior). Además dicho servidor incluye un agente capaz de notificar alarmas y tomar decisiones básicas. El sistema se comporta como se describe a continuación:

- Los **sensores** difunden información acerca de las medidas tomadas a través del servidor. Dichas medidas serían simuladas y proporcionadas mediante un formulario de entrada que proporcionara el servidor para poder incluir las medidas de ambos sensores. La introducción en el formulario de una nueva medida en cualquiera de los sensores conllevará la publicación del correspondiente evento que incluiría dicha medida. La misma página mostraría los cambios que se produzcan en el estado de los actuadores.

El **servidor** proporcionaría el formulario/página comentado en el punto anterior y la página a la que accedería el usuario tal como se comenta en el punto siguiente. Además mantendría las subscriciones, de los usuarios que se encuentren accediendo al sistema y del agente, a los eventos relacionados con luminosidad y temperatura. Y por último, guardaría un histórico de los eventos (cambios en las medidas) producidos en el sistema en una base de datos con la correspondiente marca de tiempo asociada a cada evento.

- Cada **usuario** accedería al estado del sistema a través del servidor mostrando la información en la correspondiente página que este enviaría. Dicha página mostraría las nuevas medidas que generen los sensores cuando estas se produzcan. Además el usuario podría abrir/cerrar la persiana, y/o encender/apagar el sistema de A/C en cualquier momento.

### Instrucciones para la instalación/ejecución
	
- Instalar socket.io y mongodb en el directorio donde se hayan extraido los archivos.
- Abrir una consola en el directorio donde se hayan extraido los archivos.
- Ejecutar con: ":$ nodejs servidor.js", el servidor escucha en el puerto 8080
- Abrir un navegador con las siguientes páginas: 
		localhost:8080/sensores.html 
		localhost:8080/agente.html
		localhost:8080
- Interactuar con sensores.html y con cliente.html (página por defecto)

### Funcionamiento:

- servidor.js: Lo primero que hace el servidor es dar como página por defecto "cliente.html". Este servidor guarda los estados de la persiana y el ac, por defecto ambos activos. Se conecta a la base de datos mongodb y se pone a escuchar en el puerto 8080. Una vez hecho esto, socket.io empieza a escuchar en dicho servidor. A partir de aquí, se crea la estructura de suscripción y envío de eventos. Todos los eventos se producirán en "connection". Existen eventos para cuando los sensores publican datos, para obtener los últimos datos registrados de la base de datos, para obtener los estados de la persiana o del ac, etc.

- sensores.html: Página con un formulario que simula el envío de datos de los sensores.

- cliente.html: Al entrar en esta página por primera vez, se muestra el último dato que tiene el servidor de los sensores. Se muestra, además, el estado de la persiana y el ac, con botones para cambiarlos. Por último, muestra las alertas cuando suceden por la acción del agente. Cuando se produce un cambio en la temperatura o luminosidad, se muestran automáticamente.

- agente.html: Página "vacía", que solo incluye el script para enviar alertas al servidor (tal y como está descrito en el dibujo del funcionamiento de la aplicación) cuando los sensores superan ciertos valores (25 para temperatura y 30 para luminosidad). Luego, el servidor se encarga de propagar la alerta a los clientes. Cuando se superen estos valores, el agente cierra la persiana.
