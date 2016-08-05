(define (problem Aspirante)
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
(enLoc Violeta Montana)
(enLoc Felipe Castillo)
(enLoc Quijano Lago)
(enLoc Alonso ElCruce)
(enLoc Draco Montana)
(esCasa Violeta Castillo)
(esCasa Quijano Bosque)
(esCasa Alonso Pueblo)
(esGuarida Draco Montana)
(esRey Felipe)
(esVictima Violeta)
(esAspirante Quijano)
(esAspirante Alonso)
(esDragon Draco)
(estaLibre Quijano)
(estaLibre Alonso)
(vivo Felipe)
(vivo Alonso)
(vivo Quijano)
(vivo Violeta)
(vivo Draco)
(conPrinc Draco Violeta)
(secuestrada Violeta)
)
(:goal 
(and (esHeroe Quijano) (salvada Violeta)(golpista Quijano)(libre Violeta))
)
)
