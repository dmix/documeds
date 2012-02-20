(ns documeds.templates.medications
  (:require [documeds.models.medication :as medication]
            [noir.validation :as validation]
            [documeds.templates.layouts :as layouts])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

(defpartial medication-row [{:keys [id name subtitle]}]
  [:li {:id id}
      [:b [:a {:href (str "/medication/show/" id)} name] " "
          [:a {:href (str "/items/add/" id)} "Add"]]])

(defpartial main [json]
 (layouts/application
  [:h2 "My Medication List"]
  [:table#items.table-striped
    [:thead
      [:tr [:th "Medication"] [:th "Action"]]]
    [:tbody#itemList]]
  [:script {:type "text/javascript"}
    (str "$(function() {DocuMeds.Collections.Items.reset(" json ");var view = new DocuMeds.Views.Items({collection: DocuMeds.Collections.Items,el: $('#itemList')});view.render();});")]))

(defpartial medication-list [items]
 (layouts/application
    [:h2 "Medication list"]
    [:br]
    [:ul#medications
        (map medication-row items)]
    [:br]
    [:a.btn.btn-primary {:href "/medication/new"} "New Medication"]))

(defpartial alpha-row [id]
  (let [name (medication/denormalize id)]
    [:li {:id id}
        [:b [:a {:href (str "/medication/show/" id)} name] " "
            [:a {:href (str "/items/add/" id)} "Add"]]]))

(defpartial letter-button [letter]
  [:a {:href (str "/medications/letter/" letter)} letter])

(defpartial alpha-list [letter items]
  (let [letters (map char (concat (range 97 123)))]
    (layouts/application
      [:h2 (str "Medication list: " letter)]
      [:br]
      [:div#letters 
        [:a {:href "/medications/all"} "All"]
        (map letter-button letters)]
      [:br]
      [:br]    
      [:ul#medications
        (map alpha-row items)]
      [:br]
      [:a.btn.btn-primary {:href "/medication/new"} "New Medication"])))

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