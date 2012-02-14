(ns medsource.models.keys)

(defn key-medication [id]
  (str "medications:" id))

(defn key-medications-index []
  "medications")

(defn key-increment-medications []
  "global:nextMedicationID")