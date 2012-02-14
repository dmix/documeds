(ns medsource.templates.medications
  (:require [noir.validation :as validation])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

(defpartial layout [& content]
  (html5
    [:head
      [:title "medsource"]
      (include-css "/css/reset.css")
      (include-css "/css/app.css")]
    [:body
      [:div#wrapper
        [:h1 "MediCounter"]
        content
        [:a {:href "/medications"} "Index"]
        [:a {:href "/medication/new"} "New"]]]))

(defpartial medication-index []
  (layout
    [:center 
      [:h3 "Welcome"]]))
  
(defpartial medication-row [{:keys [id title dosage]}]
  [:li {:id id}
      [:b [:a {:href (str "/medication/show/" id)} title]]
      [:span.dosage dosage]
      [:a {:href (str "/medication/edit/" id)} "Edit"]
      [:a {:href (str "/medication/delete/" id)} "Remove"]])

(defpartial medication-list [items]
 (layout
    [:h2 "Medication list"]
    [:ul#medications
        (map medication-row items)]))

(defpartial flash-notice [msg]
  (layout
    [:p msg]))

(defpartial medication-removed []
  (layout
    [:p "Medication removed!"]))

(defpartial show-medication [med]
  (layout
    [:h3 (med :title)]
    [:p (med :dosage)]))

(defpartial error-item [[first-error]]
  [:p.error first-error])

(defpartial medication-fields [{:keys [title dosage]}]
  (validation/on-error :title error-item)
  (label "title" "Title: ")
  (text-field "title" title)
  (validation/on-error :dosage error-item)
  (label "dosage" "Dosage: ")
  (text-field "dosage" dosage))

(defpartial new-medication [med]
  (layout
    (form-to [:post "/medication/new"]
        (medication-fields med)
        (submit-button "Add medication"))))
  
(defpartial edit-medication [med]
  (layout
    (form-to [:post (str "/medication/edit/" (med :id))]
        (medication-fields med)
        (submit-button "Update medication"))))