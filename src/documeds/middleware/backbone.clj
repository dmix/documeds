(ns documeds.middleware.backbone
    (:use [cheshire.core :as json]))

(defn add-json-param [handler]
  (fn [req]
    (let [neue (if (= "application/json" (get-in req [:headers "content-type"]))
      (update-in req [:params] assoc :backbone (json/parse-string (slurp (:body req)) true))
      req)]
    (handler neue))))