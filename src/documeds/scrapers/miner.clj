(ns documeds.scrapers.ncbi
  (:require [documeds.models.medication :as medication])
  (:use documeds.redis))

; Brand Miner ----------------------------------------------------------------------------

(defn process-brand [med, brand-name]
  ; create new medication with :name brand-name, :parent_id (:id med), :id_brand true
  ; add to brand index
  )

(defn mine-brand [med]
  ; if brand has content, 
  ; set new field in medication :brands name||name||name
  ; iterate through each brand
  ;    > pass med and name to process-brand
  )

(defn mine-for-brands []
  (let [meds (medication/all)]
    (map mine-brand meds)))

; Combinations Miner ---------------------------------------------------------------------

(defn find-matching-medication [name]
  ; if name = combination
  ; maybe find by slug to make it faster?
  ; return medication_id
  )

(defn find-parent-ids [parent-names]
  "Return list of parent IDs based on name||name string"
  ; let names = split || of parent-names
  (map find-matching-medication names))

(defn process-combination [med, combination-name]
  ;Process = create new medication with:
  ; :name = combination name
  ; :is_combination true
  ; split medications from Name "containing" attribute, combine as || array and store as :parent_names
  ; process parent_ids based on :parent_names and save as parent_ids attribute
  ; add medication_id to combinations index
  )

(defn mine-combination [med]
  ;if brand_names_combo isnt nil
  ;  for each p in brand_names_combo, take name and process
  ;  combination_name = p
  )

(defn mine-for-combinations []
  (let [meds (medication/all)]
    (map mine-combination meds)))

(defn -main [& args]
  (println "Running miner")
  (mine-for-combinations)
  (mine-for-brands))