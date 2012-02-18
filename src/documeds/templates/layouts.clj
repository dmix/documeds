(ns documeds.templates.layouts
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
        [:a {:href "/login"} "Log In"] " "
        [:a {:href "/signup"} "Sign Up"]
        ; [:a {:href "/logout"} "Log Out"]
        ; [:div#name "Dan McGrady"]
      ]
      [:div#wrapper
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