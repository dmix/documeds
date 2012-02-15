(ns documeds.settings)

(def redis-url (get (System/getenv) "REDIS_URL"))
(def redis-port (get (System/getenv) "REDIS_PORT"))
(def redis-pass (get (System/getenv) "REDIS_PASS"))