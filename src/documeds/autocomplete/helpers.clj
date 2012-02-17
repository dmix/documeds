(ns documeds.autocomplete.helpers
  (:require [noir.validation :as validation]))

(def min-complete 2)

(defn normalize [phrase]
  "Clean string in preperation for autocomplete query"
  (clojure.string/trim
    (clojure.string/lower-case
      (clojure.string/replace
        phrase #"[^a-z0-9 ]/i" ""))))

(defn word-prefixes [word]
  "Create list of prefixes for a word, for ex autocomplete = (au aut auto autoc etc)"
  (let [l (+ 1 (count word))
        r (range min-complete l)]
    (map (fn [num] (apply str (take num word))) r)))

(defn prefixes-for [phrase]
  "Break up words in the given phrase and find prefixes for each word individually, 
  return single list of prefixes"
  (let [words (clojure.string/split (normalize phrase) #" ")]
    (set 
      (flatten 
        (map word-prefixes words)))))