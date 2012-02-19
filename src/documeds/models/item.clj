(ns documeds.models.item
    (:require [noir.validation :as validation])
    (:use documeds.redis))

; Index ------------------------------------------------------------------------

(defn index [email]
  @(@r [:smembers (key-items-index email)]))

(defn add-index [email medication_id]
  (@r [:sadd (key-items-index email) medication_id]))

(defn remove-index [email medication_id]
  (@r [:srem (key-items-index email) medication_id]))

; Getters ------------------------------------------------------------------------

(defn retrieve [email medication_id]
  (let [i (apply hash-map @(@r [:hgetall (key-item email medication_id)]))]
    (when (not (empty? i))
      {:medication_id (i "medication_id")
       :name (i "name")
       :dosage (i "dosage")})))

(defn retrieve-multiple [ids]
 (map retrieve ids))

(defn all [email]  
 (for [medication_id (index email)
   :let [item (retrieve email medication_id)]]
  item))

; Setters ------------------------------------------------------------------------

(defn add! [email medication]
  (let [medication_id (:medication_id medication)]
    (add-index email medication_id)
    (@r [:hmset (key-item email medication_id) "medication_id" medication_id
                                               "name" (:name medication)
                                               "dosage" (:dosage medication)])))

(defn add-multiple [items]
  (dorun (map add! items)))

; Modify ------------------------------------------------------------------------

(defn remove! [email medication_id]
  (remove-index email medication_id) ; Remove id to items seq of IDs
  (@r [:del (key-item email medication_id)]))