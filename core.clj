(ns myproject.core)

(apply require clojure.main/repl-requires)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn hello [who] (str "Hello " who " !"))

(defn big 
  "Return true if string st is longer than n characters"
  [st n]
  (> (count st) n)
  )

(defn collection-type "Return the type of collection" [col]
  (if (list? col) :list 
    (if (map? col) :map
      (if (vector? col) :vector
        (if (set? col) :set
        :unknown)
      )
    )
  )
)

(defn size [v]
  (if (empty? v)
    0
    (inc (size (rest v)))))

(defn size_tailrec [v]
  (loop [local_vec v, size_count 0]
    (if (empty? local_vec)
      size_count
      (recur (rest local_vec) (inc size_count))
      )
    ) 
  )

(defmacro unless 
  ([test body] (list 'if (list 'not test) body))
  ([test body elsebody] (list 'if (list 'not test) body elsebody))
  )


(defprotocol Animal
  (animalType [this])
  (willEat [this food])
  (age [this])
)

(defrecord Pig [myAge]
  Animal
    (animalType [_] "Pig")
    (willEat [_ food]
      (if (= food "turnip") true false)
      )
    (age [_] myAge)
    )


