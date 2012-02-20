(ns documeds.views.autocomplete
  (:require [documeds.models.medication :as medication]
            [noir.response :as response]
            [noir.request :as request])
  (:use noir.core
        documeds.autocomplete.matcher))

(defpage "/autocomplete/:query" {:keys [query]}
  (let [matches (matches-for-term query)]
  (println matches)
  (response/json {:term query :results { :medication matches }})))