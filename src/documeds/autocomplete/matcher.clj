(ns documeds.autocomplete.matcher
  (:require [noir.validation :as validation])
  (:use documeds.autocomplete.helpers
        documeds.redis
        [documeds.models.medication :as medication]))

(def limit 5)

(defn validate-term-length [term]
  (let [words (normalize term)]
    (remove (fn [word] (> min-complete (count word))) (clojure.string/split words #" "))))

(defn add-cachekey [cachekey words]
  (if (not (redis-has-key cachekey))
    (let [interkeys (map (fn [w] (str key-autocomplete w)) words)]
      @(@r (into [] (flatten [:zinterstore cachekey (count interkeys) interkeys])))
      @(@r [:expire cachekey (* 10 60)]))))

(defn matches-for-term [term]
  (let [words (validate-term-length term)]
    (when (not (empty? words))
      (let [cachekey (str key-cachebase (clojure.string/join "|" words))]
        (add-cachekey cachekey words)
        (let [ids @(@r [:zrevrange cachekey 0 (- limit 1)])]
          (if (> (count ids) 0)
            (retrieve-multiple ids)))))))