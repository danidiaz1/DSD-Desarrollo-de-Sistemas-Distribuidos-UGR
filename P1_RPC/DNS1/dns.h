/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#ifndef _DNS_H_RPCGEN
#define _DNS_H_RPCGEN

#include <rpc/rpc.h>
#include <cstdio>
#include <map>
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

#define DNS 0x20000002
#define DNSVERS 2
#if defined(__STDC__) || defined(__cplusplus)
#define RESOLVER_NOMBRE1 1
extern  char ** resolver_nombre_1_svc(char *, struct svc_req *);
#define REGISTRAR_EQUIPO1 2
extern  int * registrar_equipo_1_svc(char *, char *, struct svc_req *);
#define BORRAR_EQUIPO1 3
extern  int * borrar_equipo_1_svc(char *, struct svc_req *);
extern int dns_1_freeresult (SVCXPRT *, xdrproc_t, caddr_t);

#else /* K&R C */
#define RESOLVER_NOMBRE1 1
extern  char ** resolver_nombre_1_svc();
#define REGISTRAR_EQUIPO1 2
extern  int * registrar_equipo_1_svc();
#define BORRAR_EQUIPO1 3
extern  int * borrar_equipo_1_svc();
extern int dns_1_freeresult ();
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

#endif /* !_DNS_H_RPCGEN */
