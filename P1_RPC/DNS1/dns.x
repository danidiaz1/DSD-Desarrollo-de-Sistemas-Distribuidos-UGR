/* Archivo dns.x: Protocolo para sumar dos números */

program DNS {
	version DNSVERS {
		string RESOLVER_NOMBRE (string) = 1;
		int REGISTRAR_EQUIPO (string, string) = 2;
		int BORRAR_EQUIPO (string) = 3;
		string ENVIAR_PETICION (string) = 4;
	} = 1;
} = 0x20000001;
