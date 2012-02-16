(ns documeds.middleware.ssl
  :require [clojure.contrib.string :as contrib])

(defn https-url [request-url]
  (str "https://" (:server-name request-url) (:uri request-url)))

(defn require-https
  [handler]
  (fn [request]
    (if (= (:server-name request-url) "documeds.com")
      (if (= (:scheme request) :http)
        (ring.util.response/redirect (https-url request))
        (handler request)))))