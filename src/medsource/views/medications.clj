(ns medsource.views.medications
  (:require [medsource.models.medication :as medication]
            [medsource.templates.medications :as t]
            [noir.response :as response])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

(defpage "/" {}
  (let [items (medication/all)]
    (t/medication-list items)))

(defpage "/medication/new" {:as med}
  (t/new-medication med))

(defpage [:post "/medication/new"] {:as med}
  (if (medication/valid? med)
    (do 
      (medication/add! {"title" (med :title) "dosage" (med :dosage)})
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
      (response/redirect "/"))))

(defpage "/medication/show/:id" {:keys [id]}
  (let [med (medication/retrieve id)]
    (t/show-medication med)))

(defpage "/medication/delete/:id" {:keys [id]}
  (let [med (medication/retrieve id)]
    (medication/remove! med)
    (t/flash-notice "Medication Removed!")
    (response/redirect "/")))