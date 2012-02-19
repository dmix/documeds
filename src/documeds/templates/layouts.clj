(ns documeds.templates.layouts
  (:require [noir.session :as sess]
            [noir.options :as options])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

(defn javascript-assets []
  (if (options/dev-mode?)
    (include-js "/assets/vendor.js"
                "/assets/autocomplete.js"
                "/assets/app.js"
                "/assets/items.js"
                "/assets/functions.js"
                "/dusts/items/row.js"
                "/dusts/autocomplete/result.js")
    (include-js "/assets/vendor.js" "/assets/production.js")))

(defn css-assets []
  (if (options/dev-mode?)
    (include-css "/css/bootstrap.css"
                 "/css/app.css")
    (include-css "/assets/production.css")))

(defpartial application [& content]
  (html5
    [:head
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
            [:a {:href "/items"} "My Items"]
            [:a {:href ""} [:img {:src (str "/img/country/" (clojure.string/lower-case (sess/get :country)) ".png")}]]
            [:div#name (sess/get :email)]
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
        [:ul#itemList]
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