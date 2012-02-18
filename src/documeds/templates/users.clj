(ns documeds.templates.users
  (:require [documeds.templates.layouts :as layouts]
            [noir.validation :as validation])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

(defpartial error-item [[first-error]]
  [:p.error first-error])

(defpartial signup-fields [{:keys [username email]}]
  [:p
    (validation/on-error :username error-item)
    (label "username" "Username: ")
    (text-field "username" username)]
  [:p 
    (validation/on-error :email error-item)
    (label "email" "Email: ")
    (text-field "email" email)])

(defpartial login-fields [{:keys [email password]}]
  [:p 
    (validation/on-error :email error-item)
    (label "email" "Email: ")
    (text-field "email" email)]
  [:p
    (validation/on-error :password error-item)
    (label "password" "Password: ")
    (text-field "password" password)])

(defpartial new-user [user]
  (layouts/application
    (form-to [:post "/user/new"]
      (signup-fields user)
      [:input {:type "submit" :class "btn" :value "Create Account"}])))

(defpartial new-session [user]
  (layouts/application
    (form-to [:post "/session/new"]
      (login-fields user)
      [:input {:type "submit" :class "btn" :value "Create Account"}])))