/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#ifndef _DNS2_H_RPCGEN
#define _DNS2_H_RPCGEN

#include <rpc/rpc.h>
#include <cstdio>
#include <string>
#include <iostream>


#ifdef __cplusplus
extern "C" {
#endif


struct registrar_equipo_1_argument {
	char *arg1;
	char *arg2;
};
typedef struct registrar_equipo_1_argument registrar_equipo_1_argument;

#define DNS2 0x20000001
#define DNS2VERS 1

#if defined(__STDC__) || defined(__cplusplus)
#define RESOLVER_NOMBRE 5
extern  char ** resolver_nombre_1(char *, CLIENT *);
extern  char ** resolver_nombre_1_svc(char *, struct svc_req *);
#define REGISTRAR_EQUIPO 6
extern  int * registrar_equipo_1(char *, char *, CLIENT *);
extern  int * registrar_equipo_1_svc(char *, char *, struct svc_req *);
#define BORRAR_EQUIPO 7
extern  int * borrar_equipo_1(char *, CLIENT *);
extern  int * borrar_equipo_1_svc(char *, struct svc_req *);
#define ENVIAR_PETICION 8
extern  char ** enviar_peticion_1(char *, CLIENT *);
extern  char ** enviar_peticion_1_svc(char *, struct svc_req *);
extern int dns2_1_freeresult (SVCXPRT *, xdrproc_t, caddr_t);

#else /* K&R C */
#define RESOLVER_NOMBRE 5
extern  char ** resolver_nombre_1();
extern  char ** resolver_nombre_1_svc();
#define REGISTRAR_EQUIPO 6
extern  int * registrar_equipo_1();
extern  int * registrar_equipo_1_svc();
#define BORRAR_EQUIPO 7
extern  int * borrar_equipo_1();
extern  int * borrar_equipo_1_svc();
#define ENVIAR_PETICION 8
extern  char ** enviar_peticion_1();
extern  char ** enviar_peticion_1_svc();
extern int dns2_1_freeresult ();
#endif /* K&R C */

/* the xdr functions */

#if defined(__STDC__) || defined(__cplusplus)
extern  bool_t xdr_registrar_equipo_1_argument (XDR *, registrar_equipo_1_argument*);

#else /* K&R C */
extern bool_t xdr_registrar_equipo_1_argument ();

#endif /* K&R C */

#ifdef __cplusplus
}
#endif

#endif /* !_DNS2_H_RPCGEN */
