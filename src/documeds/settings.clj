(ns documeds.settings)

;redis://redistogo:527d2a676998e2ccb850c25677d3cc8c@dogfish.redistogo.com:9583/
; (def redis-url (get (System/getenv) "REDIS_URL"))
; (def redis-port (Integer/parseInt (get (System/getenv) "REDIS_PORT")))
; (def redis-pass (get (System/getenv) "REDIS_PASS"))

(def redis-url "dogfish.redistogo.com")
(def redis-pass "527d2a676998e2ccb850c25677d3cc8c")
(def redis-port 9583)