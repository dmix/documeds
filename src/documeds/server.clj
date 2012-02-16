(ns documeds.server
  (:require [noir.server :as server]
            [documeds.middleware.ssl :as ssl]))

(server/load-views "src/documeds/views/")
(server/add-middleware ssl/require-https)

(defn -main [& m]
  (let [mode (or (first m) :dev)
        port (Integer. (get (System/getenv) "PORT" "5000"))]
    (server/start port {:mode (keyword mode)
                        :ns 'documeds})))