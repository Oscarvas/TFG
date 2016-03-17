(define (problem Caballero)
(:domain Historia)

(:objects
Castillo
Bosque
Pueblo
Lago
ElCruce
Montana
Alonso
Felipe
Encantador
Quijano
Laura
Violeta
Draco
)
(:init
(adyacente Montana Lago)
(adyacente Pueblo Castillo)
(adyacente Castillo Bosque)
(adyacente Castillo Pueblo)
(adyacente Castillo ElCruce)
(adyacente Lago Castillo)
(adyacente Lago Montana)
(adyacente Lago ElCruce)
(adyacente Bosque Castillo)
(adyacente Bosque ElCruce)
(adyacente ElCruce Castillo)
(adyacente ElCruce Bosque)
(adyacente ElCruce Lago)
(locSegura Castillo)
(enLoc Violeta Montana)
(enLoc Laura Castillo)
(enLoc Felipe Castillo)
(enLoc Quijano Pueblo)
(enLoc Alonso Pueblo)
(enLoc Encantador Pueblo)
(enLoc Draco Montana)
(esCasa Violeta Castillo)
(esCasa Laura Castillo)
(esCasa Felipe Castillo)
(esCasa Quijano Pueblo)
(esCasa Alonso Pueblo)
(esGuarida Draco Montana)
(esRey Felipe)
(esPrincipal Felipe)
(esPrincesa Laura)
(esPrincipal Laura)
(esPrincesa Violeta)
(esPrincipal Violeta)
(esCaballero Alonso)
(esSecundario Alonso)
(esCaballero Quijano)
(esSecundario Quijano)
(esDragon Draco)
(esSecundario Draco)
(estaLibre Alonso)
(estaLibre Encantador)
(estaLibre Quijano)
(vivo Alonso)
(vivo Felipe)
(vivo Encantador)
(vivo Quijano)
(vivo Laura)
(vivo Violeta)
(vivo Draco)
(conPrinc Draco Violeta)
(secuestrada Violeta)
)
(:goal 
(and (golpista Quijano)(esHeroe Quijano) (salvada Violeta))
)
)
