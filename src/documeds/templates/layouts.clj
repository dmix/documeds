(ns documeds.templates.layouts
  (:require [noir.session :as sess])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

(defpartial application [& content]
  (html5
    [:head
      [:title "Medication Tracker | DocuMeds"]
      (include-js "/assets/vendor.js")
      (include-js "/assets/autocomplete.js")
      (include-js "/assets/app.js")
      (include-js "/assets/functions.js")
      (include-css "/css/bootstrap.css")
      (include-css "/css/app.css")]
    [:body
      [:div#top
        (if-not (sess/get :email)
          [:div#loggedout
            [:a {:href "/login"} "Log In"] " "
            [:a {:href "/signup"} "Sign Up"]]
          [:div#loggedin
            [:a {:href "/logout"} "Log Out"]
            [:div#name (sess/get :email)]
            [:img {:src (str "/img/country/" (clojure.string/lower-case (sess/get :country)) ".png")}]
            ])
      ]
      [:div#wrapper
        (when-let [message (sess/flash-get)]
          [:div#flash message])
        [:a {:href "/medications" :id "logo"} [:img {:src "/img/logo.png"}]]
        [:div#search
          [:input {:class "text" :id "autocomplete" :type "text"}]
          [:button {:class "btn btn-primary"} "Go"]]
        [:div.spacer]
        [:div#results
          [:ul#resultsList]]
        content " "
        [:br]
        [:a.btn.btn-primary {:href "/medication/new"} "New Medication"]]]))

(defpartial landing []
  (html5
    [:head
      [:title "Medication Tracker | DocuMeds"]
      (include-css "/css/bootstrap.css")
      (include-css "/css/app.css")]
    [:body
      [:div#central
        [:img {:src "/img/logo.png"}]
        [:br][:br][:br][:br]
        [:h5 "Helping to make taking medication
              <span>safer</span>,<br> more <span>effective</span> & <span>anxiety-free</span>."]
        [:br][:br][:br][:br][:br][:br]
        [:div.small "Follow development on "
          [:a {:href "https://github.com/dmix/documeds"} "Github"]]]]))

(defn flash! [message]
  (sess/flash-put! message)
  nil)