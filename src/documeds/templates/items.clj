(ns documeds.templates.items
  (:require [noir.validation :as validation]
            [documeds.templates.layouts :as layouts])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

(defpartial item-row [{:keys [medication_id name dosage]}]
  [:li {:id medication_id}
      [:b [:a {:href (str "/medication/show/" medication_id)} name] " "
          [:a {:href (str "/items/delete/" medication_id)} "Remove"]]])

(defpartial item-list [items]
 (layouts/application
    [:h2 "My Item list"]
    [:br]
    [:ul#items
        (map item-row items)]))