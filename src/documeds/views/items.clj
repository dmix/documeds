(ns documeds.views.items
  (:require [documeds.models.user :as user]
            [documeds.models.medication :as medication]
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


(pre-route "/items*" []
  (cond  (not (sess/get :email)) (response/redirect "/login")))

(defpage "/items" {}
  (let [items (item/all (email))]
    ; (t/item-list items)
    (response/json items)))

(defpage [:post "/items"] {:as item}
  (let [medication_id (:medication_id (:backbone item))
        med (medication/retrieve medication_id)]
    (item/add! (email) {:medication_id (med :id) :name (med :name) :dosage 50})
    (response/json med)))

(defpage "/items/add/:medication_id" {:keys [medication_id]}
  (let [med (medication/retrieve medication_id)]
    (do 
      (item/add! (email) {:medication_id (med :id) :name (med :name) :dosage 50})
      (layouts/flash! "Item created!")
      (response/redirect "/items"))))

(defpage "/items/delete/:medication_id" {:keys [medication_id]}
  (let [i (item/retrieve (email) medication_id)]
    (item/remove! (email) medication_id)
    (layouts/flash! "Item Removed!")
    (response/redirect "/items")))