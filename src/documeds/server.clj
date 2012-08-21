(ns documeds.server
  (:require [noir.server :as server]
            [documeds.middleware.ssl :as ssl]
            [documeds.middleware.backbone :as backbone]))

(server/add-middleware ssl/require-https)
(server/add-middleware backbone/add-json-param)
(server/load-views "src/documeds/views/")

(defn -main [& m]
  (let [mode (or (first m) :dev)
        port (Integer. (get (System/getenv) "PORT" "5000"))]
    (server/start port {:mode (keyword mode)
                        :ns 'documeds})))

; lein run -m documeds.server :prod -d