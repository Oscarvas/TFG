(define (domain Historia)
	(:requirements :typing)
	(:types
		localizacion
		allegado
		aspirante
		secuestrador
		victima
		ladron
		emboscador
		asesino
		ayudante
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
		(esAllegado ?per)
		(esVictima ?per)
		(esAspirante ?per)
		(esAyudante ?per)
		(esDruida ?per)
		(esSecuestrador ?per)
		(esLadron ?per)
		(esAsesino ?per)
		(esEmboscador ?per)
		
		(vivo ?per)
	)

;; escape de la victima
	(:action moverPrincipal
		:parameters (?per ?locOrig ?locDest)
		:precondition (and	
			(vivo ?per)
			(adyacente ?locOrig ?locDest)
			(enLoc ?per ?locOrig)
			(esVictima ?per) 
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
	
	
;; un drag�n se mueve con la victima entre sus zarpas

	(:action moverPersonajeConVictima
		:parameters (?per ?p ?locOrig ?locDest)
		:precondition (and
			(esSecundario ?per)
			(esVictima ?p)
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

;; batalla entre fantasma y allegado
	(:action venganza
		:parameters (?p1 ?p2 ?loc)
		:precondition (and			
			(not (= ?p1 ?p2))
			(enLoc ?p1 ?loc)
			(enLoc ?p2 ?loc)
			(esAsesino ?p1)
			(esAllegado ?p2)
			(vivo ?p1)
			(vivo ?p2))
		:effect
			(derrocar ?p2)
	)
	
;; batalla entre aspirante y Secuestrador
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
	
	;; batalla entre secndario y victima
	(:action criminal
		:parameters (?c ?p ?loc)
		:precondition (and
			(esSecundario ?c)
			(esVictima ?p)
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


;; el aspirante escolta a la victima
	(:action liberarVictima
		:parameters (?c ?p ?d ?loc)
		:precondition (and
			(esAspirante ?c)
			(esVictima ?p)
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


;; un drag�n secuestra a una victima
	(:action secuestrar
		:parameters (?d ?p ?loc)
		:precondition (and
			(vivo ?d)
			(vivo ?p)
			(esSecuestrador ?d)
			(estaLibre ?d)
			(esVictima ?p)
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



;; el aspirante deja a la victima en su hogar
	(:action dejarEnCasa
		:parameters (?c ?p ?loc)
		:precondition (and
			(esAspirante ?c)
			(esVictima ?p)
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
		
		
;; el aspirante se convierte en h�roe
	(:action convertirseEnHeroe
		:parameters (?c ?locCab)
		:precondition (and
			(esAspirante ?c)
			(vivo ?c)
			(enLoc ?c ?locCab)
			(esCasa ?c ?locCab)
			(salvador ?c)
		)
		:effect 
			(esHeroe ?c)
	)
	
	;; el aspirante se convierte en villano
	(:action convertirseEnVillano
		:parameters (?c ?locCab)
		:precondition (and
			(esAspirante ?c)
			(vivo ?c)
			(enLoc ?c ?locCab)
			(esCasa ?c ?locCab)
			(asesino ?c)
		)
		:effect 
			(esVillano ?c)
	)

;; el ladron consigue un objeto
	(:action proteger
		:parameters (?g ?o ?loc)
		:precondition (and
			(vivo ?g)
			(objetoEnLoc ?o ?loc)
			(esLadron ?g)
			(estaLibre ?g)
			(enLoc ?g ?loc)
		)
		:effect (and
			(not (estaLibre ?g))
			(conObjeto ?g ?o)
			(cansado ?g)
		)
	)
	
;; El ladron se mueve con un objeto
	(:action escoltarObjetoRobado
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

;; El ladron se mueve con un objeto
	(:action escoltarObjeto
		:parameters (?per ?o ?locOrigen ?locDest)
		:precondition (and
			(esSecundario ?per)
			(conObjeto ?per ?o)
			(vivo ?per)
			(objetoEnLoc ?o ?locOrigen)
			(enLoc ?per ?locOrigen)
			(adyacente ?locOrigen ?locDest)
			(not (cansado ?per))
		)
		:effect (and
			(enLoc ?per ?locDest)
			(objetoEnLoc ?o ?locDest)
			(not (enLoc ?per ?locOrigen))
			(not (objetoEnLoc ?o ?locOrigen))
		)
	)

;; batalla entre ayudante y Ladron
	(:action batallaArcana
		:parameters (?mg ?g ?loc)
		:precondition (and
			(esSecundario ?mg)
			(esSecundario ?g)
			(esAyudante ?mg)
			(esLadron ?g)
			(not (= ?mg ?g))
			(enLoc ?mg ?loc)
			(enLoc ?g ?loc)
			(esGuarida ?g ?loc)
			(vivo ?mg)
			(vivo ?g)
		)
		:effect (and
			(ayudante ?mg)
			(not (vivo ?g))
		)
	)

;; el ayudante consigue el objeto del ladron
	(:action cogerObjeto
		:parameters (?mg ?g ?o ?loc)
		:precondition (and
			(esAyudante ?mg)
			(esLadron ?g)
			(vivo ?mg)
			(not (vivo ?g))
			(objetoEnLoc ?o ?loc)
			(enLoc ?mg ?loc)
			(enLoc ?g ?loc)
			(ayudante ?mg)
		)
		:effect (and
			(not (estaLibre ?mg))
			(conObjeto ?mg ?o)
		)
	)
	


;; el ayudante restaura el objeto en su hogar
	(:action restaurarObjeto
		:parameters (?mg ?o ?loc)
		:precondition (and
			(esAyudante ?mg)
			(conObjeto ?mg ?o)
			(enLoc ?mg ?loc)
			(objetoEnLoc ?o ?loc)
			(esCasa ?mg ?loc)
		)
		:effect (and
			(enLoc ?mg ?loc)
			(objetoEnLoc ?o ?loc)
			(restaurado ?o)
			(estaLibre ?mg)
			(restauradorClasico ?mg)
		)
	)	

;; el ayudante se convierte en sabio
	(:action convertirseEnSabio
		:parameters (?mg ?locMg)
		:precondition (and
			(esAyudante ?mg)
			(vivo ?mg)
			(enLoc ?mg ?locMg)
			(esCasa ?mg ?locMg)
			(restauradorClasico ?mg)
		)
		:effect
			(esSabio ?mg)
	)

)