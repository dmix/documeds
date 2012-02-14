(ns medsource.server
  (:require [noir.server :as server]))

(server/load-views "src/medsource/views/")

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8888"))]
    (server/start port {:mode mode
                        :ns 'medsource})))