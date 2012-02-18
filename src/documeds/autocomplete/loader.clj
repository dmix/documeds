(ns documeds.autocomplete.loader
  (:require [noir.validation :as validation])
  (:use documeds.autocomplete.helpers
        documeds.redis
        [documeds.models.medication :as medication]))

(def temp (list {:id "503", :name "Mexiletine", :subtitle "subtitle"} {:id "422", :name "Triamcinolone Topical", :subtitle "subtitle"} {:id "341", :name "Fluoride", :subtitle "subtitle"} {:id "260", :name "Stool Softeners", :subtitle "subtitle"} {:id "504", :name "Thalidomide", :subtitle "subtitle"} {:id "423", :name "Saquinavir", :subtitle "subtitle"} {:id "342", :name "Probenecid", :subtitle "subtitle"} {:id "261", :name "Goserelin Implant", :subtitle "subtitle"} {:id "180", :name "Cimetidine", :subtitle "subtitle"} {:id "505", :name "Estradiol Topical", :subtitle "subtitle"} {:id "424", :name "Oxiconazole", :subtitle "subtitle"} {:id "343", :name "Dutasteride", :subtitle "subtitle"} {:id "50666", :name "Probase", :subtitle "subtitle"}))

(defn store-index [prefix]
  "Store each prefix into master set"
  @(@r [:sadd key-autocomplete prefix]))

(defn store-kv [prefix id]
  "Store key/value to redis ex 'clomate-index:probe id'"
  @(@r [:zadd (str key-autocomplete prefix) 0 id]))

(defn create-index-for [med]
  "Retrieve prefixes for the medication name and store index for each into redis"
  (let [prefixes (prefixes-for (:name med)) id (:id med)]
    (map (fn [prefix]
      (store-kv prefix id)
      (store-index prefix)) prefixes)))

(defn store-indexes []
  "Iterate through all medications in the redis database and create index"
  (let [meds (medication/all)]
    (flatten (map create-index-for meds))))