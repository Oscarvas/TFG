(define (domain Historia)
	
	(:types
		localizacion
		rey
		caballero
		secuestrador
		princesa
	)
	
	(:predicates
		(adyacente ?l1 ?l2)
		(enLoc ?per ?loc)
		(locSegura ?loc)
		
		(estaLibre ?per)
		
		(conPrinc ?d ?p)
		
		(esPrincipal ?per)
		(esSecundario ?per)
		(esRey ?r)
		(esPrincesa ?p)
		(esCaballero ?c)
		(esSecuestrador ?d)
		
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
	
	
;; un personaje secundario se mueve a una localizaci�n adyacente
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
	
	
;; un drag�n se mueve con la princesa entre sus zarpas

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


;; un drag�n secuestra a una princesa
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
		
		
;; el caballero se convierte en h�roe
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

)