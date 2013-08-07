(ns myproject.barber)
(apply require clojure.main/repl-requires)

(def maxWaiting 3)
(def haircutDuration 20)

(def isRunning (atom false))

(def hairCutsGiven (ref 0))
(def customersGenerated (ref 0))
(def customersLost (ref 0))

(def customersWaiting (ref 0))
(def barberBusy (ref false))

(def seatsAvailable? #(< @customersWaiting maxWaiting))
(def workToDo? #(and @isRunning (> @customersWaiting 0)))

(defn resetState []
  (reset! isRunning false)
  (dosync
    (ref-set hairCutsGiven 0)
    (ref-set customersGenerated 0)
    (ref-set customersLost 0)
    (ref-set customersWaiting 0)
    (ref-set barberBusy false)
    )
  )

(defn printState []
  (println "isRunning" @isRunning", given" @hairCutsGiven "haircuts, generated" @customersGenerated
          ", lost" @customersLost "," @customersWaiting "waiting now")
  )
  
(defn customerArrived
  []
    (dosync (alter customersGenerated inc))
    (if (< @customersWaiting maxWaiting)
      (dosync (alter customersWaiting inc))
      (dosync (alter customersLost inc))
    )
  )
    
(defn generateCustomers
  []
    (loop [shouldRun isRunning]
      (if (false? @shouldRun)
        0
        (
          let []
            (java.lang.Thread/sleep (+ 10 (rand-int 21)))
            (customerArrived)
            (recur isRunning)
            )
      )
  )
)

(defn giveHaircut []
  (dosync (alter customersWaiting dec) (ref-set barberBusy true))
  (java.lang.Thread/sleep haircutDuration)
  (dosync (alter hairCutsGiven inc) (ref-set barberBusy false))
  )
  
(defn barberWorker []
  (while @isRunning
    (if (workToDo?)
      (giveHaircut)
      )
    )
  )

(defn statusReporter []
  (while @isRunning
    (java.lang.Thread/sleep 1000)
    (printState)
    )
  )

(defn runSim
  [maxSeconds]
      (resetState)
      (reset! isRunning true)
      (future (generateCustomers))
      (future (barberWorker))
      (future (statusReporter))
      (java.lang.Thread/sleep (* maxSeconds 1000))
      (reset! isRunning false)
      (printState)
    )
    
      