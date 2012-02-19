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
(defn key-alpha-index [letter] (str "medications:alpha:" letter))


; User Model Keys --------------------------------------------
(defn key-user [id] (str "users:" id))
(defn key-users-index [] "users")
(defn key-increment-users [] "global:nextUserID")


; Autocomplete Keys -------------------------------------------------
(def key-autocomplete "clomate-index:")
(def key-database     "clomate-data:")
(def key-cachebase    "clomate-cache:")


; Item Keys ---------------------------------------------------------
(defn key-item [email medication_id] (str "items:" email ":" medication_id)) ; items:dan@dmix.ca:1000 = hash
(defn key-items-index [email] (str "items:" email)) ; items:dan@dmix.ca = seq of item ids


(defn redis-has-key [redis-key]
  (= @(@r [:exists redis-key]) 1))
  
