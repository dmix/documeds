(ns documeds.middleware.ssl)

(defn https-url [request-url]
  (str "https://" (:server-name request-url) (:uri request-url)))

(defn require-https
  "Redirect to https if the current request was from http"
  [handler]
  (fn [request]
    (if (and (= (:server-name request) "documeds.com") (= (:scheme request) :http))
      (ring.util.response/redirect (https-url request)))
      (handler request)))