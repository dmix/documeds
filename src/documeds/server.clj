(ns documeds.server
  (:require [noir.server :as server]
            [clojure.contrib.string :as contrib]))

(server/load-views "src/documeds/views/")

(defn https-url [request-url]
  (str "https://" (:server-name request-url) (:uri request-url)))

(defn require-https
  [handler]
  (fn [request]
    (if (= (:server-name request-url) "documeds.com")
      (if (= (:scheme request) :http)
        (ring.util.response/redirect (https-url request))
        (handler request)))))

(server/add-middleware require-https)

(defn -main [& m]
  (let [mode (or (first m) :dev)
        port (Integer. (get (System/getenv) "PORT" "5000"))]
    (server/start port {:mode (keyword mode)
                        :ns 'documeds})))