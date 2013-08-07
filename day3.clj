(ns myproject.day3)
(apply require clojure.main/repl-requires)


(defn create []
  (atom {})
  )

(defn cget [cache key]
  (@cache key)
  )

(defn cput 
  [cache key value] (swap! cache assoc key value)
  )

(defn twice [x] (* 2 x))



(defn createBank [] (atom {:bank "MyBank"}))

(defn createAccount [bank name] 
  (swap! bank assoc name (atom 0 :meta {:name name}))
  )


(defn getAccount [bank name]
  (@bank name)
  )

(defn debitAccount [bank name amount]
  (swap! (getAccount bank name) (partial + (- amount)))
  )

(defn creditAccount [bank name amount]
  (swap! (getAccount bank name) (partial + amount))
  )


(defn createRefBank
  [] {:name "RefBank" :accounts {}}
  )

(defn getAccount
  [rb name]
      (if (not (contains? (rb :accounts) name)) 
          (assoc @rb :accounts (assoc (rb :accounts) name 0)) 
          )
      )

                   
                   