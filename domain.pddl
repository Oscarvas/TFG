(define (domain Historia)
	
	(:types
		localizacion
		rey
		caballero
		secuestrador
		princesa
		guardian
		emboscador
		maligno
		mago
		druida
	)
	
	(:predicates
		(adyacente ?l1 ?l2)
		(enLoc ?per ?loc)
		(locSegura ?loc)
		
		(estaLibre ?per)
		
		(conPrinc ?d ?p)
		(conObjeto ?per ?o)
		(objetoEnLoc ?o ?loc)
		
		(esPrincipal ?per)
		(esSecundario ?per)
		(esRey ?r)
		(esPrincesa ?p)
		(esCaballero ?c)
		(esMago ?mg)
		(esDruida ?c)
		(esSecuestrador ?d)
		(esGuardian ?per)
		(esMaligno ?m)
		(esEmboscador ?e)
		
		(vivo ?per)
	)

;; escape de la princesa
	(:action moverPrincipal
		:parameters (?per ?locOrig ?locDest)
		:precondition (and	
			(vivo ?per)
			(adyacente ?locOrig ?locDest)
			(enLoc ?per ?locOrig)
			(esPrincesa ?per) 
			(estaLibre ?per))
		:effect (and
			(enLoc ?per ?locDest)
			(not (enLoc ?per ?locOrig)))
	)
	
	
;; un personaje secundario se mueve a una localización adyacente
	(:action moverSecundario
		:parameters (?pers ?locOrig ?locDest)
		:precondition (and	
			(vivo ?pers)
			(adyacente ?locOrig ?locDest)
			(enLoc ?pers ?locOrig)
			(esSecundario ?pers)
			(estaLibre ?pers)
			(not (cansado ?pers))
			(not (esHeroe ?pers)))
		:effect (and
			(enLoc ?pers ?locDest)
			(not (enLoc ?pers ?locOrig)))
	)
	
	
;; un dragón se mueve con la princesa entre sus zarpas

	(:action moverPersonajeConPrincesa
		:parameters (?per ?p ?locOrig ?locDest)
		:precondition (and
			(esSecundario ?per)
			(esPrincesa ?p)
			(vivo ?per)
			(adyacente ?locOrig ?locDest)
			(enLoc ?per ?locOrig)
			(not (esGuarida ?per ?locOrig))
			(conPrinc ?per ?p))
		:effect (and
			(enLoc ?per ?locDest)
			(enLoc ?p ?locDest)
			(not (enLoc ?per ?locOrig))
			(not (enLoc ?p ?locOrig)))
	)

;; batalla entre fantasma y rey
	(:action venganza
		:parameters (?p1 ?p2 ?loc)
		:precondition (and			
			(not (= ?p1 ?p2))
			(enLoc ?p1 ?loc)
			(enLoc ?p2 ?loc)
			(esMaligno ?p1)
			(esRey ?p2)
			(vivo ?p1)
			(vivo ?p2))
		:effect
			(derrocar ?p2)
	)
	
;; batalla entre caballero y Secuestrador
	(:action batalla
		:parameters (?c ?d ?loc)
		:precondition (and
			(esSecundario ?c)
			(esSecundario ?d)
			(not (= ?c ?d))
			(enLoc ?c ?loc)
			(enLoc ?d ?loc)
			(esGuarida ?d ?loc)
			(cansado ?d)
			(vivo ?c)
			(vivo ?d))
		:effect (and
			(libertador ?c)
			(not (vivo ?d)))
	)
	
	;; batalla entre secndario y princesa
	(:action criminal
		:parameters (?c ?p ?loc)
		:precondition (and
			(esSecundario ?c)
			(esPrincesa ?p)
			(not (= ?c ?p))
			(enLoc ?c ?loc)
			(enLoc ?p ?loc)
			(libertador ?c)
			(secuestrada ?p)
			(vivo ?c)
			(vivo ?p))
		:effect (and			
			(asesino ?c)
			(not (vivo ?p)))
	)


;; el caballero escolta a la princesa
	(:action liberarPrincesa
		:parameters (?c ?p ?d ?loc)
		:precondition (and
			(esCaballero ?c)
			(esPrincesa ?p)
			(esSecuestrador ?d)	
			(vivo ?c)
			(vivo ?p)
			(not (vivo ?d))
			(enLoc ?c ?loc)
			(enLoc ?p ?loc)
			(libertador ?c)
			(conPrinc ?d ?p)
			(estaLibre ?c))
		:effect (and
			(conPrinc ?c ?p)
			(not (conPrinc ?d ?p))
			(not (estaLibre ?c)))
	)


;; un dragón secuestra a una princesa
	(:action secuestrar
		:parameters (?d ?p ?loc)
		:precondition (and
			(vivo ?d)
			(vivo ?p)
			(esSecuestrador ?d)
			(estaLibre ?d)
			(esPrincesa ?p)
			(not (salvada ?p))
			(not (secuestrada ?p))
			(enLoc ?d ?loc)
			(enLoc ?p ?loc))
		:effect (and
			(not (estaLibre ?d))
			(cansado ?d)
			(conPrinc ?d ?p)
			(secuestrada ?p))
	)

;; el guardian consigue un objeto
	(:action proteger
		:parameters (?g ?o ?loc)
		:precondition (and
			(vivo ?g)
			(objetoEnLoc ?o ?loc)
			(esGuardian ?g)
			(estaLibre ?g)
			(enLoc ?g ?loc))
		:effect (and
			(not (estaLibre ?g))
			(conObjeto ?g ?o))
	)
	
;; El guardian se mueve con un objeto

(:action escoltarObjeto
	:parameters (?per ?o ?locOrigen ?locDest)
	:precondition (and
		(esSecundario ?per)
		(conObjeto ?per ?o)
		(vivo ?per)
		(objetoEnLoc ?o ?locOrigen)
		(enLoc ?per ?locOrigen)
		(adyacente ?locOrigen ?locDest)
	)
	:effect (and
		(enLoc ?per ?locDest)
		(objetoEnLoc ?o ?locDest)
		(not (enLoc ?per ?locOrigen))
		(not (objetoEnLoc ?o ?locOrigen))
	)
)

;; el caballero deja a la princesa en su hogar
	(:action dejarEnCasa
		:parameters (?c ?p ?loc)
		:precondition (and
			(esCaballero ?c)
			(esPrincesa ?p)
			(enLoc ?c ?loc)
			(conPrinc ?c ?p)
			(esCasa ?p ?loc))
		:effect (and
			(not (secuestrada ?p))
			(not (conPrinc ?c ?p))
			(salvada ?p)
			(estaLibre ?c)
			(salvador ?c))
	)		
		
		
;; el caballero se convierte en héroe
	(:action convertirseEnHeroe
		:parameters (?c ?locCab)
		:precondition (and
			(esCaballero ?c)
			(vivo ?c)
			(enLoc ?c ?locCab)
			(esCasa ?c ?locCab)
			(salvador ?c))
		:effect 
			(esHeroe ?c)
	)
	
	;; el caballero se convierte en héroe
	(:action convertirseEnVillano
		:parameters (?c ?locCab)
		:precondition (and
			(esCaballero ?c)
			(vivo ?c)
			(enLoc ?c ?locCab)
			(esCasa ?c ?locCab)
			(asesino ?c))
		:effect 
			(esVillano ?c)
	)

)