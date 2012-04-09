(ns documeds.templates.blogs
  (:require [noir.session :as sess]
            [noir.validation :as validation]
            [documeds.templates.layouts :as layouts])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

(defpartial posts []
  (html5
    [:head
      [:title "Medication Tracker | DocuMeds"]
      (include-css "/css/bootstrap.css")
      (include-css "/css/app.css")]
    [:body
      [:div#top
        [:div#loggedout
          [:a {:href "#coming_soon"} "Home"]
          [:a {:href "/blog"} "Blog"]
          [:a {:href "http://twitter.com/documeds"} "Twitter"]]]
      [:center
        [:br][:br][:br][:br][:br][:br][:br][:br]
        [:h2 "Blog coming soon"]]]))