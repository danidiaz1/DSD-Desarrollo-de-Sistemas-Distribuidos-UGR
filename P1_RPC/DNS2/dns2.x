/* Archivo dns2.x: Protocolo para sumar dos números */

program DNS2 {
	version DNS2VERS {
		string RESOLVER_NOMBRE (string) = 5;
		int REGISTRAR_EQUIPO (string, string) = 6;
		int BORRAR_EQUIPO (string) = 7;
		string ENVIAR_PETICION (string) = 8;
	} = 1;
} = 0x20000001;
