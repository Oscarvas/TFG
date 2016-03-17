(define (domain Historia)
	
	(:types
		localizacion
		rey
		caballero
		villano
		dragon
		princesa
	)
	
	(:predicates
		(adyacente ?l1 ?l2)
		(enLoc ?per ?loc)
		(locSegura ?loc)
		
		(estaLibre ?per)
		
		(conPrinc ?d ?p)
		(conPrinc ?v ?p)
		
		(esPrincipal ?per)
		(esSecundario ?per)
		(esRey ?r)
		(esPrincesa ?p)
		(esCaballero ?c)
		(esVillano ?v)
		(esDragon ?d)
		
		(vivo ?per)
	)


	
;; un personaje secundario se mueve a una localizaciÃ³n adyacente
	(:action mover
		:parameters (?pers ?locOrig ?locDest)
		:precondition (and	
			(vivo ?pers)
			(adyacente ?locOrig ?locDest)
			(enLoc ?pers ?locOrig)
			(esSecundario ?pers)
			(estaLibre ?pers)
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


;; batalla entre caballero y dragon
	(:action batalla
		:parameters (?p1 ?p2 ?loc)
		:precondition (and			
			(not (= ?p1 ?p2))
			(not (esPrincesa ?p1))
			(enLoc ?p1 ?loc)
			(enLoc ?p2 ?loc)
			(vivo ?p1)
			(vivo ?p2))
		:effect 
			(not (vivo ?p2))
	)
;; batalla entre caballero y dragon
	(:action escapar
		:parameters (?p1 ?p2 ?loc)
		:precondition (and			
			(not (= ?p1 ?p2))
			(esPrincesa ?p1)
			(esDragon ?p2)
			(enLoc ?p1 ?loc)
			(enLoc ?p2 ?loc)
			(vivo ?p1)
			(vivo ?p2))
		:effect 
			(not (vivo ?p2))
	)
;; batalla entre caballero y dragon
	(:action golpeDeEstado
		:parameters (?p1 ?p2 ?loc)
		:precondition (and			
			(not (= ?p1 ?p2))
			(enLoc ?p1 ?loc)
			(enLoc ?p2 ?loc)
			(esRey ?p2)
			(vivo ?p1)
			(vivo ?p2))
		:effect (and
			(derrocado ?p2)
			(golpista ?p1))
	)

;; el caballero escolta a la princesa
	(:action liberarPrincesa
		:parameters (?c ?p ?d ?loc)
		:precondition (and
			(esCaballero ?c)
			(esPrincesa ?p)
			(esDragon ?d)	
			(vivo ?c)
			(vivo ?p)
			(not (vivo ?d))
			(enLoc ?c ?loc)
			(enLoc ?p ?loc)
			
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
			(esDragon ?d)
			(estaLibre ?d)
			(esPrincesa ?p)
			(not (salvada ?p))
			(not (secuestrada ?p))
			(enLoc ?d ?loc)
			(enLoc ?p ?loc))
		:effect (and
			(not (estaLibre ?d))
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
		
		
;; el caballero se convierte en heroe
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