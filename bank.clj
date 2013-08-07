(ns myproject.bank)
(apply require clojure.main/repl-requires)

(defn validBalance [bal] (>= bal 0))

(defn makeAccount
  []
    (ref 0 :validator validBalance)
  )

(defn makeBank 
  [numAccounts]
   (vec (repeatedly numAccounts makeAccount))
    )

(defn getAccount
  [bank accountNo]
    (nth bank accountNo)
    )

(defn creditAccount
  [bank accountNo amount]
    (alter (getAccount bank accountNo) + amount)
    )

(defn debitAccount
  [bank accountNo amount]
    (alter (getAccount bank accountNo) - amount)
    )

(defn transfer
  [bank acc1 acc2 amount]
    (dosync (debitAccount bank acc1 amount) (creditAccount bank acc2 amount))
    )

