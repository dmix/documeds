(ns documeds.views.autocomplete
  (:require [documeds.models.medication :as medication]
            [noir.response :as response]
            [noir.request :as request])
  (:use noir.core
        documeds.autocomplete.matcher))

(defpage "/clomate/:query" {:keys [query]}
  (let [matches (matches-for-term query)]
  (response/json matches)))