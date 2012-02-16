(ns documeds.middleware.ssl
  (:require [clojure.contrib.string :as contrib]))

(defn https-url [request-url]
  (str "https://" (:server-name request-url) (:uri request-url)))

(defn require-https
  [handler]
  (fn [request]
    (if (= (:scheme request) :http)
      (if (= (:server-name request) "documeds.com")
        (ring.util.response/redirect (https-url request)))
        (handler request))))