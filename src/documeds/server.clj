(ns documeds.server
  (:require [noir.server :as server]
            [documeds.middleware.ssl :as ssl])
  (:use aleph.redis
        documeds.settings))

(def r (redis-client {:host redis-url :password redis-pass :port redis-port}))

(server/load-views "src/documeds/views/")
(server/add-middleware ssl/require-https)

(defn -main [& m]
  (let [mode (or (first m) :dev)
        port (Integer. (get (System/getenv) "PORT" "5000"))]
    (server/start port {:mode (keyword mode)
                        :ns 'documeds})))