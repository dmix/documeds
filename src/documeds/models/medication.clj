; "Schema" --------------------------------------------------------------------
;
; medications:<ID> = {
;     id: medication id
;     title: medication tile
;     dosage: description of the dosage season
; }
;
; (add! {"title" "Tylneol" "dosage" "10mg"})

(ns documeds.models.medication
    (:require [noir.validation :as validation])
    (:use aleph.redis
          documeds.settings
          documeds.models.keys))

(def r (redis-client {:host redis-url :password redis-pass :port redis-port}))

; Index ------------------------------------------------------------------------

(defn index []
  (r [:smembers (key-medications-index)]))

(defn add-index [id]
  (r [:sadd (key-medications-index) id]))

(defn remove-index [id]
  (r [:srem (key-medications-index) id]))

(defn increment []
  (r [:incr (key-increment-medications)]))

; Getters ------------------------------------------------------------------------

(defn retrieve [id]
  (let [medication (apply hash-map (r [:hgetall (key-medication id)]))]
    (when (not (empty? medication))
      {:id (medication "id")
       :title (medication "title")
       :dosage (medication "dosage")})))

(defn all []  
  (for [id (index)
    :let [medication (retrieve id)]]
   medication))

; Setters ------------------------------------------------------------------------

(defn set-id [id new-id]
  (r [:hset (key-medication id) "id" new-id]))

(defn set-title [id new-title]
  (r [:hset (key-medication id) "title" new-title]))

(defn set-dosage [id new-dosage]
  (r [:hset (key-medication id) "dosage" new-dosage]))

(defn add! [medication]
  (let [id (increment)
        title (medication "title")
        dosage (medication "dosage")]
    (add-index id) ; Add id to medications seq of IDs
    (r [:hmset (key-medication id) "id" id 
                                    "title" title 
                                    "dosage" dosage])))

(defn add-multiple [medications]
  (dorun (map add! medications)))

; Modify ------------------------------------------------------------------------

(defn update! [medication]
  (let [id (medication :id)
        title (medication :title)
        dosage (medication :dosage)]
    (r [:hmset (key-medication id) "id" id
                                    "title" title 
                                    "dosage" dosage])))

(defn remove! [medication]
  (let [id (medication :id)]
    (remove-index id) ; Remove id to medications seq of IDs
    (r [:del (key-medication id)])))

; Validation ------------------------------------------------------------------------

(defn valid? [{:keys [title dosage]}]
  (validation/rule (validation/min-length? title 4)
      [:title "Your title must have more than 5 letters."])
  (validation/rule (validation/has-value? dosage)
      [:dosage "You must set a dosage"])
  (not (validation/errors? :title :dosage)))

