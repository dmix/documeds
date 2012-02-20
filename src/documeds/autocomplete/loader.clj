(ns documeds.autocomplete.loader
  (:require [noir.validation :as validation])
  (:use documeds.autocomplete.helpers
        documeds.redis
        [documeds.models.medication :as medication]))

(defn store-index [prefix]
  "Store each prefix into master set"
  @(@r [:sadd key-autocomplete prefix]))

(defn store-kv [prefix id]
  "Store key/value to redis ex 'clomate-index:probe id'"
  @(@r [:zadd (str key-autocomplete prefix) 0 id]))

(defn create-index-for [med]
  "Retrieve prefixes for the medication name and store index for each into redis"
  (let [prefixes (prefixes-for (:name med)) id (:id med)]
    (map (fn [prefix]
      (store-kv prefix id)
      (store-index prefix)) prefixes)))

(defn store-indexes []
  "Iterate through all medications in the redis database and create index"
  (let [meds (medication/all)]
    (flatten (map create-index-for meds))))

(defn -main [& args]
  (println "Running indexer")
  (store-indexes)
  (medication/build-alpha-index))