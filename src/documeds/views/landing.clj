(ns documeds.views.landing
  (:require [documeds.templates.layouts :as layouts]
            [noir.response :as response]
            [noir.options :as options]
            [noir.request :as request]
            [noir.session :as sess])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

(defn javascript-assets []
  (if (options/dev-mode?)
    (include-js "/assets/vendor/jquery.js"
                "/assets/vendor/underscore.js"
                "/assets/vendor/bootstrap-modals.js"
                "/assets/landing.js"
                "/assets/functions.js")
    (include-js "/assets/production-landing.js")))

(defn css-assets []
  (if (options/dev-mode?)
    (include-css "/css/bootstrap.css"
                 "/css/landing.css")
    (include-css "/assets/production-landing.css")))

(defpartial modal [header button & content]
  [:div.modal-header
    [:a {:class "close" :data-dismiss "modal"} "x"]
    [:h3 header]]
  [:div.modal-body content]
  [:div.modal-footer
    [:a {:href "#" :class "btn btn-primary"} button]
    [:a {:href "#" :class "btn" :data-dismiss "modal"} "Close"]])

(defpartial survey []
  [:div#surveyForm
    [:div#surveySuccess.none
      [:br]
      [:img {:src "mark.png"}]
      [:h2 "Thanks for your answers!"]
      [:h4 "We appreciate all help."]]
    [:div#surveyQuestions
      [:div.dim "Help us improve MediCounter by answering our quick 5 question survey!"]
      [:h4
        "Question"
        [:div#count "1"]
        "of 5"]
        [:div.question]
          [:div.num "1."]
          [:label "How many different medications do you take each day?"]
          [:div.answer
            [:label{:for "survey_different_meds_0"}
              [:input {:type "radio" :name "different_meds" :value "0"}]
              "None"]
            [:label{:for "survey_different_meds_1"}
              [:input {:type "radio" :name "different_meds" :value "1"}]
              "1-5"]
            [:label{:for "survey_different_meds_5"}
              [:input {:type "radio" :name "different_meds" :value "5"}]
              "5-10"]
            [:label{:for "survey_different_meds_10"}
              [:input {:type "radio" :name "different_meds" :value "10"}]
              ">10"]]]

        [:div.question.hide
          [:div.num "2."]
          [:label "Do you find it difficult to keep track of all medications you take?"]
          [:div.answer
            [:label{:for "survey_different_meds_0"}
              [:input {:type "radio" :name "different_meds" :value "0"}]
              "Yes, it can be overwhelming"]
            [:label{:for "survey_different_meds_1"}
              [:input {:type "radio" :name "different_meds" :value "1"}]
              "Nope, its straightforward"]]]

        [:div.question.hide
          [:div.num "3."]
          [:label "Are you comfortable discussing the medications you take with others who have similar health conditions?"]
          [:div.answer
            [:label{:for "survey_different_meds_0"}
              [:input {:type "radio" :name "different_meds" :value "0"}]
              "Yes"]
            [:label{:for "survey_different_meds_5"}
              [:input {:type "radio" :name "different_meds" :value "5"}]
              "Yes, but only anonymously"]
            [:label{:for "survey_different_meds_1"}
              [:input {:type "radio" :name "different_meds" :value "1"}]
              "No"]]]

        [:div.question.hide
          [:div.num "4."]
          [:label "Have you ever gotten a second doctors opinion on a medication you were prescribed?"]
          [:div.answer
            [:label{:for "survey_different_meds_0"}
              [:input {:type "radio" :name "different_meds" :value "0"}]
              "Yes"]
            [:label{:for "survey_different_meds_1"}
              [:input {:type "radio" :name "different_meds" :value "0"}]
              "No"]]]

        [:div.question.hide
          [:div.num "5."]
          [:label "Do you own a smartphone?"]
          [:div.answer
            [:label{:for "survey_different_meds_0"}
              [:input {:type "radio" :name "different_meds" :value "0"}]
              "Nope"]
            [:label{:for "survey_different_meds_1"}
              [:input {:type "radio" :name "different_meds" :value "0"}]
              "iPhone"]
            [:label{:for "survey_different_meds_5"}
              [:input {:type "radio" :name "different_meds" :value "0"}]
              "Android"]
            [:label{:for "survey_different_meds_5"}
              [:input {:type "radio" :name "different_meds" :value "0"}]
              "Other"]]]])

(defpage "/" {}
  (html5
    [:head
      [:title "Medication Tracker | DocuMeds"]
      (css-assets)]
    [:body
      [:div#top
        (if-not (sess/get :email)
          [:div#loggedout
            [:a {:href "/blog"} "Blog"]
            [:a {:href "http://twitter.com/documeds"} "Twitter"]]
          [:div#loggedin
            [:a {:href "/logout"} "Log Out"]
            [:a {:href "/medications/letter/a"} "All Medications"]
            [:a {:href ""} [:img {:src (str "/img/country/" (layouts/country) ".png")}]]
            [:div#name (sess/get :email)]])]
      [:div#landing
        [:div#logoBox
          [:a {:href "#coming_soon" :id "early"}]]
        [:img {:src "/img/documeds.png" :id "documeds"}]
        [:div#github.small "Follow development on "
          [:a {:href "https://github.com/dmix/documeds"} "Github"]
          " and updates on "
          [:a {:href "https://github.com/dmix/documeds"} "Twitter"]]]
      [:div#survey.modal.none (modal "Survey" "Next Question" (survey))]
      (javascript-assets)]))