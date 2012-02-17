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
    (:use documeds.redis))

; Index ------------------------------------------------------------------------

(defn index []
  @(@r [:smembers (key-medications-index)]))

(defn add-index [id]
  (@r [:sadd (key-medications-index) id]))

(defn remove-index [id]
  (@r [:srem (key-medications-index) id]))

(defn increment []
  @(@r [:incr (key-increment-medications)]))

; Getters ------------------------------------------------------------------------

(defn retrieve [id]
  {
    :id @(@r [:hget (key-medication id) "id"])
    :name @(@r [:hget (key-medication id) "name"])
    :subtitle "subtitle"
  })
  ; (let [m (apply hash-map @(@r [:hgetall (key-medication id)]))]
  ;   (when (not (empty? m))
  ;     {:id id
  ;      :name (m "name")
  ;      :subtitle (m "subtitle")
  ;      :why (m "why")
  ;      :how (m "how")
  ;      :side_effects (m "side_effects")
  ;      :other_uses (m "other_uses")
  ;      :other_information (m "other_information")
  ;      :precautions (m "precautions")
  ;      :dietary (m "dietary")
  ;      :brand_names (m "brand_names")
  ;      ; :brand_names_combo (m "brand_names_combo")
  ;      :overdose (m "overdose")
  ;      :if_i_forget (m "if_i_forget")
  ;      :slug (m "slug")})))

(defn all []  
  (for [id (index)
    :let [medication (retrieve id)]]
   medication))

; Setters ------------------------------------------------------------------------

(defn add! [m]
  (let [id (increment)]
    (add-index id) ; Add id to medications seq of IDs
    @(@r [:hmset (key-medication id) "id" id 
                                     "name" (:name m)
                                     "subtitle" (:subtitle m)
                                     "why" (:why m)
                                     "how" (:how m)
                                     "side_effects" (:side_effects m)
                                     "other_uses" (:other_uses m)
                                     "other_information" (:other_information m)
                                     "precautions" (:precautions m)
                                     "dietary" (:dietary m)
                                     "brand_names" (:brand_names m)
                                     ; "brand_names_combo" (:brand_names_combo m)
                                     "overdose" (:overdose m)
                                     "if_i_forget" (:if_i_forget m)
                                     "slug" (:slug m)])))

(defn add-multiple [medications]
  (dorun (map add! medications)))

; Modify ------------------------------------------------------------------------

(defn update! [medication]
  (let [id (medication :id)
        title (medication :title)
        dosage (medication :dosage)]
    (@r [:hmset (key-medication id) "id" id
                                    "title" title 
                                    "dosage" dosage])))

(defn remove! [medication]
  (let [id (medication :id)]
    (remove-index id) ; Remove id to medications seq of IDs
    (@r [:del (key-medication id)])))

; Validation ------------------------------------------------------------------------

(defn valid? [{:keys [title dosage]}]
  (validation/rule (validation/min-length? title 4)
      [:title "Your title must have more than 5 letters."])
  (validation/rule (validation/has-value? dosage)
      [:dosage "You must set a dosage"])
  (not (validation/errors? :title :dosage)))
