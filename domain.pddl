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
		:parameters (?c ?d ?loc)
		:precondition (and
			(esSecundario ?c)
			(esSecundario ?d)
			(not (= ?c ?d))
			(enLoc ?c ?loc)
			(enLoc ?d ?loc)
			(vivo ?c)
			(vivo ?d))
		:effect 
			(not (vivo ?d))
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
		
		
;; el caballero se convierte en hÃ©roe
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
	
	
;;adaptaciones del villano


;; batalla entre villano y dragon
	(:action batalla
		:parameters (?v ?d ?loc)
		:precondition (and
			(esSecundario ?v)
			(esSecundario ?d)
			(not (= ?v ?d))
			(enLoc ?v ?loc)
			(enLoc ?d ?loc)
			(vivo ?v)
			(vivo ?d))
		:effect 
			(not (vivo ?d))
	)
	
	
;; batalla entre villano y caballero
	(:action batalla
		:parameters (?v ?c ?loc)
		:precondition (and
			(esSecundario ?v)
			(esSecundario ?c)
			(not (= ?v ?c))
			(enLoc ?v ?loc)
			(enLoc ?c ?loc)
			(vivo ?v)
			(vivo ?c))
		:effect 
			(not (vivo ?v))
	)

;; un villano secuestra a una princesa
	(:action secuestrar
		:parameters (?v ?p ?loc ?d)
		:precondition (and
			(vivo ?v)
			(vivo ?p)
			(esVillano ?v)
			(estaLibre ?v)
			(esPrincesa ?p)
			(not (salvada ?p))
			(not (vivo ?d))
			(secuestrada ?p)
			(enLoc ?v ?loc)
			(enLoc ?p ?loc))
		:effect (and
			(not (estaLibre ?v))
			(conPrinc ?v ?p)
			(secuestrada ?p))
	)
	
)