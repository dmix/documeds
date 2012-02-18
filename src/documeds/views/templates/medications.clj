(ns documeds.views.templates.medications
  (:require [noir.validation :as validation])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

(defpartial layout [& content]
  (html5
    [:head
      [:title "Medication Tracker | DocuMeds"]
      (include-js "/assets/vendor.js")
      (include-js "/assets/autocomplete.js")
      (include-js "/assets/app.js")
      (include-css "/css/bootstrap.css")
      (include-css "/css/app.css")]
    [:body
      [:div#wrapper
        [:h1 [:a {:href "/medications"} "docu" [:span "meds"]]]
        content " "
        [:br]
        [:a.btn.btn-primary {:href "/medication/new"} "New Medication"]]]))

(defpartial welcome []
  (html5
    [:head
      [:title "Medication Tracker | DocuMeds"]
      (include-css "/css/bootstrap.css")
      (include-css "/css/app.css")]
    [:body
      [:div#central
        [:h1 "docu" [:span "meds"]]
        [:h5 "Helping to make taking medication
              <span>safer</span>,<br> more <span>effective</span> & <span>anxiety-free</span>."]
        [:br][:br][:br][:br][:br][:br]
        [:div.small "Follow development on "
          [:a {:href "https://github.com/dmix/documeds"} "Github"]]]]))
  
(defpartial medication-row [{:keys [id name subtitle]}]
  [:li {:id id}
      [:b [:a {:href (str "/medication/show/" id)} name]] "  "
      [:span.subtitle subtitle] "  "
      [:a {:href (str "/medication/edit/" id)} "Edit"] "  "
      [:a {:href (str "/medication/delete/" id)} "Remove"]])

(defpartial medication-list [items]
 (layout
    [:h2 "Medication list"]
    [:input {:class "text" :id "autocomplete" :type "text"}]
    [:ul#results]
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
    [:h3 (med :name)]
    [:p (med :subtitle)]))

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
  (layout
    (form-to [:post "/medication/new"]
      (medication-fields med)
      [:input {:type "submit" :class "btn" :value "Add Medication"}])))
  
(defpartial edit-medication [med]
  (layout
    (form-to [:post (str "/medication/edit/" (med :id))]
        (medication-fields med)
        [:input {:type "submit" :class "btn" :value "Update Medication"}])))
