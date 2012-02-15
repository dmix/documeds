(ns documeds.server
  (:require [noir.server :as server]))

(server/load-views "src/documeds/views/")

(defn -main [port & m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" port))]
    (server/start port {:mode mode
                        :ns 'documeds})))