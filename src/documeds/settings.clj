(ns documeds.settings)

(def redis-uri (get (System/getenv) "REDIS_URI"))
(def redis-url (get (System/getenv) "REDIS_URL"))
(def redis-port (Integer/parseInt (get (System/getenv) "REDIS_PORT")))
(def redis-pass (get (System/getenv) "REDIS_PASS"))