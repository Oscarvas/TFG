
(define (problem gripper2)
    (:domain gripper-typed)
  (:objects roomA roomB - room Ball1 Ball2 - ball)


  (:init 
     (at-robby roomA) 
     (free left) 
     (free right)  
     (at Ball1 roomA)
     (at Ball2 roomA))

  (:goal (and (at Ball1 roomB) (at Ball2 roomB))))