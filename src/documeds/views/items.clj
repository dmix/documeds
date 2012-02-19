(ns documeds.views.items
  (:require [documeds.models.medication :as medication]
            [documeds.models.item :as item]
            [documeds.templates.layouts :as layouts]
            [documeds.templates.items :as t]
            [noir.response :as response]
            [noir.session :as sess]
            [noir.request :as request])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

(defn email [] (sess/get :email))

(defpage "/items" {}
  (let [items (item/all (email))]
    (t/item-list items)))

(defpage "/item/add/:medication_id" {:keys [medication_id]}
  (let [med (medication/retrieve medication_id)]
    (do 
      (item/add! (email) {:medication_id (med :id) :name (med :name) :dosage 50})
      (layouts/flash! "Item created!")
      (response/redirect "/items"))))

(defpage "/item/delete/:medication_id" {:keys [medication_id]}
  (let [i (item/retrieve (email) medication_id)]
    (item/remove! (email) medication_id)
    (layouts/flash! "Item Removed!")
    (response/redirect "/items")))