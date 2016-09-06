(define (domain Historia)
	
	(:types
		localizacion
		rey
		aspirante
		villano
		dragon
		victima
	)
	
	(:predicates
		(adyacente ?l1 ?l2)
		(enLoc ?per ?loc)
		(esRey ?r)
		(esVictima ?p)
		(esAspirante ?c)
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
			(estaLibre ?pers)
			(not (esHeroe ?pers)))
		:effect (and
			(enLoc ?pers ?locDest)
			(not (enLoc ?pers ?locOrig)))
	)
	
	
;; un dragón se mueve con la victima entre sus zarpas

	(:action moverPersonajeConVictima
		:parameters (?per ?p ?locOrig ?locDest)
		:precondition (and
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


;; batalla entre aspirante y dragon
	(:action combateHeroico
		:parameters (?p1 ?p2 ?loc)
		:precondition (and			
			(not (= ?p1 ?p2))
			(esAspirante ?p1)
			(esDragon ?p2)
			(enLoc ?p1 ?loc)
			(enLoc ?p2 ?loc)
			(vivo ?p1)
			(vivo ?p2))
		:effect 
			(not (vivo ?p2))
	)

;; batalla entre aspirante y dragon
	(:action ajusteDeCuentas
		:parameters (?p1 ?p2 ?loc)
		:precondition (and			
			(not (= ?p1 ?p2))
			(enLoc ?p1 ?loc)
			(enLoc ?p2 ?loc)
			(esAspirante ?p2)
			(vivo ?p1)
			(vivo ?p2))
		:effect
			(putote ?p1)
	)

;; batalla entre aspirante y dragon
	(:action golpeDeEstado
		:parameters (?p1 ?p2 ?loc)
		:precondition (and			
			(not (= ?p1 ?p2))
			(enLoc ?p1 ?loc)
			(enLoc ?p2 ?loc)
			(putote ?p1)
			(esRey ?p2)
			(vivo ?p1)
			(vivo ?p2))
		:effect (and
			(derrocado ?p2)
			(golpista ?p1))
	)

;; el aspirante escolta a la victima
	(:action liberarVictima
		:parameters (?c ?p ?d ?loc)
		:precondition (and
			(esAspirante ?c)
			(esVictima ?p)
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


;; un dragón secuestra a una victima
	(:action secuestrar
		:parameters (?d ?p ?loc)
		:precondition (and
			(vivo ?d)
			(vivo ?p)
			(esDragon ?d)
			(estaLibre ?d)
			(esVictima ?p)
			(not (salvada ?p))
			(not (secuestrada ?p))
			(enLoc ?d ?loc)
			(enLoc ?p ?loc))
		:effect (and
			(not (estaLibre ?d))
			(conPrinc ?d ?p)
			(secuestrada ?p))
	)

;; el aspirante deja a la victima en su hogar
	(:action dejarEnCasa
		:parameters (?c ?p ?loc)
		:precondition (and
			(esAspirante ?c)
			(esVictima ?p)
			(conPrinc ?c ?p)
			(enLoc ?c ?loc)
			(esCasa ?p ?loc))
		:effect (and
			(not (secuestrada ?p))
			(not (conPrinc ?c ?p))
			(salvada ?p)
			(estaLibre ?c)
			(salvador ?c))
	)		
		
		
;; el aspirante se convierte en heroe
	(:action convertirseEnHeroe
		:parameters (?c ?locCab)
		:precondition (and
			(esAspirante ?c)
			(vivo ?c)
			(enLoc ?c ?locCab)
			(esCasa ?c ?locCab)
			(salvador ?c))
		:effect 
			(esHeroe ?c)
	)	
;; batalla entre aspirante y dragon
	(:action escapar
		:parameters (?p1 ?p2 ?loc)
		:precondition (and			
			(not (= ?p1 ?p2))
			(esVictima ?p1)
			(esDragon ?p2)
			(enLoc ?p1 ?loc)
			(enLoc ?p2 ?loc)
			(vivo ?p1)
			(vivo ?p2))
		:effect 
			(libre ?p1)
	)
)