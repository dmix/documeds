(ns documeds.middleware.ssl)

(defn https-url [request-url]
  (str "https://" (:server-name request-url) (:uri request-url)))

(defn require-https
  [handler]
  (fn [request]
    (if (and (= (:server-name request) "documeds.com") (= (:scheme request) :http))
      (ring.util.response/redirect (https-url request)))
      (handler request)))