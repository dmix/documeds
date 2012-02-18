(ns documeds.templates.medications
  (:require [noir.validation :as validation]
            [documeds.templates.layouts :as layouts])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

(defpartial medication-row [{:keys [id name subtitle]}]
  [:li {:id id}
      [:b [:a {:href (str "/medication/show/" id)} name]]])

(defpartial medication-list [items]
 (layouts/application
    [:h2 "Medication list"]
    [:br]
    [:ul#medications
        (map medication-row items)]))

(defpartial medication-removed []
  (layouts/application
    [:p "Medication removed!"]))

(defpartial show-medication [med]
  (layouts/application
    [:h3 (med :name)]
    [:p (med :subtitle)]
    [:a {:href (str "/medication/edit/" (med :id))} "Edit"] "  "
    [:a {:href (str "/medication/delete/" (med :id))} "Remove"][:br]))

(defpartial error-item [[first-error]]
  [:p.error first-error])

(defpartial medication-fields [{:keys [name subtitle]}]
  [:p
    (validation/on-error :name error-item)
    (label "name" "Name: ")
    (text-field "name" name)]
  [:p 
    (validation/on-error :subtitle error-item)
    (label "subtitle" "Dosage: ")
    (text-field "subtitle" subtitle)])

(defpartial new-medication [med]
  (layouts/application
    (form-to [:post "/medication/new"]
      (medication-fields med)
      [:input {:type "submit" :class "btn" :value "Add Medication"}])))

(defpartial edit-medication [med]
  (layouts/application
    (form-to [:post (str "/medication/edit/" (med :id))]
        (medication-fields med)
        [:input {:type "submit" :class "btn" :value "Update Medication"}])))