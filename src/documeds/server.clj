(ns documeds.server
  (:require [noir.server :as server]))

(server/load-views "src/documeds/views/")

(defn -main [& m]
  (server/start (Integer. (get (System/getenv) "PORT")) {:mode :dev :ns 'documeds}))