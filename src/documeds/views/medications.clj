(ns documeds.views.medications
  (:require [documeds.models.medication :as medication]
            [documeds.templates.medications :as t]
            [noir.response :as response]
            [noir.request :as request])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

(defpage "/" {}
  (t/welcome))

(defpage "/medications" {}
  (let [items (medication/all)]
    (response/redirect "http://localhost:5000/GOOO")
    (t/medication-list items)))

(defpage "/medication/new" {:as med}
  (t/new-medication med))

(defpage [:post "/medication/new"] {:as med}
  (if (medication/valid? med)
    (do 
      (medication/add! {"name" (med :name) "subtitle" (med :subtitle)})
      (t/flash-notice "Medication Created!")
      (response/redirect "/medications"))))

(defpage "/medication/edit/:id" {:keys [id]}
  (let [med (medication/retrieve id)]
    (t/edit-medication med)))

(defpage [:post "/medication/edit/:id"] {:as med}
  (if (medication/valid? med)
    (do 
      (medication/update! med)
      (t/flash-notice "Medication Updated")
      (response/redirect "/medications"))))

(defpage "/medication/show/:id" {:keys [id]}
  (let [med (medication/retrieve id)]
    (t/show-medication med)))

(defpage "/medication/delete/:id" {:keys [id]}
  (let [med (medication/retrieve id)]
    (medication/remove! med)
    (t/flash-notice "Medication Removed!")
    (response/redirect "/medications")))