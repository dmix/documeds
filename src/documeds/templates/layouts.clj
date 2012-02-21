(ns documeds.templates.layouts
  (:require [noir.session :as sess]
            [noir.options :as options])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

(defn country []
  (let [country (sess/get :country)])
    (clojure.string/lower-case
      (if (nil? country)
        "CA"
        country)))

(defn javascript-assets []
  (if (options/dev-mode?)
    (include-js "/assets/autocomplete.js"
                "/assets/app.js"
                "/assets/items.js"
                "/assets/functions.js"
                "/dusts/items/row.js"
                "/dusts/items/dosage.js"
                "/dusts/autocomplete/result.js")
    (include-js "/assets/production.js")))

(defn css-assets []
  (if (options/dev-mode?)
    (include-css "/css/bootstrap.css"
                 "/css/app.css")
    (include-css "/assets/production.css")))

(defpartial application [& content]
  (html5
    [:head
      (include-js "/assets/vendor.js")
      [:title "Medication Tracker | DocuMeds"]
      (css-assets)]
    [:body
      [:div#top
        (if-not (sess/get :email)
          [:div#loggedout
            [:a {:href "/login"} "Log In"] " "
            [:a {:href "/signup"} "Sign Up"]]
          [:div#loggedin
            [:a {:href "/logout"} "Log Out"]
            [:a {:href "/medications/letter/a"} "All Medications"]
            [:a {:href ""} [:img {:src (str "/img/country/" (country) ".png")}]]
            [:div#name (sess/get :email)]])]
      [:div#modal]
      [:div#wrapper
        (when-let [message (sess/flash-get)]
          [:div#flash message])
        [:a {:href "/medications" :id "logo"} [:img {:src "/img/logo.png"}]]
        [:div#search
          [:input {:class "text" :id "autocomplete" :type "text" :autocomplete "off" :name "q"}]
          [:button {:class "btn btn-primary"} "Go"]]
        [:div.spacer]
        [:div#results
          [:ul#resultsList]]
        content " "
        [:br]]
        (javascript-assets)]))

(defpartial landing []
  (html5
    [:head
      [:title "Medication Tracker | DocuMeds"]
      (include-css "/css/bootstrap.css")
      (include-css "/css/app.css")]
    [:body
      [:div#top
        (if-not (sess/get :email)
          [:div#loggedout
            [:a {:href "/login"} "Log In"] " "
            [:a {:href "/blog"} "Blog"]
            [:a {:href "/blog"} "Twitter"]]
          [:div#loggedin
            [:a {:href "/logout"} "Log Out"]
            [:a {:href "/medications/letter/a"} "All Medications"]
            [:a {:href ""} [:img {:src (str "/img/country/" (country) ".png")}]]
            [:div#name (sess/get :email)]])]
      [:div#landing
        [:div#logoBox
          [:a {:href "#" :id "early"}]]
        [:img {:src "/img/documeds.png" :id "documeds"}]
        [:div#github.small "Follow development on "
          [:a {:href "https://github.com/dmix/documeds"} "Github"]
          " and updates on "
          [:a {:href "https://github.com/dmix/documeds"} "Twitter"]]]]))

(defn flash! [message]
  (sess/flash-put! message)
  nil)