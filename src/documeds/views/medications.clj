(ns documeds.views.medications
  (:require [documeds.models.medication :as medication]
            [documeds.models.item :as item]
            [documeds.templates.layouts :as layouts]
            [documeds.templates.medications :as t]
            [noir.response :as response]
            [noir.request :as request]
            [noir.session :as sess])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

(defn email [] (sess/get :email))

(defpage "/" {}
  (layouts/landing))

(pre-route "/medications*" []
  (cond  (not (sess/get :email)) (response/redirect "/login")))

(defpage "/medications" {}
  (let [items (item/all (email))]
    ; (t/item-list items)
    (t/main (:body (response/json items)))))

(defpage "/medications/all" {}
  (let [items (medication/index)]
    (t/alpha-list "ALL" items)))

(defpage "/medications/letter/:letter" {:keys [letter]}
  (let [items (medication/alpha-index letter)]
    (t/alpha-list letter items)))

(defpage "/medication/new" {:as med}
  (t/new-medication med))

(defpage [:post "/medication/new"] {:as med}
  (if (medication/valid? med)
    (do 
      (medication/add! {"name" (med :name) "subtitle" (med :subtitle)})
      (layouts/flash! "Medication created!")
      (response/redirect "/medications"))))

(defpage "/medication/edit/:id" {:keys [id]}
  (let [med (medication/retrieve id)]
    (t/edit-medication med)))

(defpage [:post "/medication/edit/:id"] {:as med}
  (if (medication/valid? med)
    (do 
      (medication/update! med)
      (layouts/flash! "Medication Updated")
      (response/redirect "/medications"))))

(defpage "/medication/show/:id" {:keys [id]}
  (let [med (medication/retrieve id)]
    (t/show-medication med)))

(defpage "/medication/delete/:id" {:keys [id]}
  (let [med (medication/retrieve id)]
    (medication/remove! med)
    (layouts/flash! "Medication Removed!")
    (response/redirect "/medications")))