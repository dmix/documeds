(ns documeds.redis
  (:use [aleph.redis :only (redis-client)]))


; Redis Connection -----------------------------------------------
(def redis-uri (get (System/getenv) "REDIS_URI"))
(def redis-url (get (System/getenv) "REDIS_URL"))
(def redis-port (Integer/parseInt (get (System/getenv) "REDIS_PORT")))
(def redis-pass (get (System/getenv) "REDIS_PASS"))

; (def l (delay (redis-client {:host "localhost"})))
(def r (delay (redis-client {:host redis-url 
                             :password redis-pass 
                             :port redis-port})))


; Medication Model Keys --------------------------------------------
(defn key-medication [id] (str "medications:" id))
(defn key-medications-index [] "medications")
(defn key-increment-medications [] "global:nextMedicationID")


; Autocomplete Keys -------------------------------------------------
(def key-autocomplete "clomate-index:")
(def key-database     "clomate-data:")
(def key-cachebase    "clomate-cache:")

(defn redis-has-key [redis-key]
  (= @(@r [:exists redis-key]) 1))