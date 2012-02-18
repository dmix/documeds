(ns documeds.views.users
  (:require [documeds.models.user :as user]
            [documeds.templates.layouts :as layouts]
            [documeds.templates.users :as t]
            [noir.response :as response]
            [noir.request :as request]
            [noir.session :as sess]
            [noir.util.crypt :as crypt])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

; Header Data ------------------------------------------------------------------------

(defn ip []
  (:headers (request/ring-request)))

(defn country []
  "CN")

; Authentication --------------------------------------------------------------
(defn force-login []
  (layouts/flash! "Please log in to view that page!")
  (response/redirect "/"))

(defmacro login-required [& body]
  `(if-not (sess/get :email)
     (force-login)
     (do ~@body)))

(defpage "/login" {:as user}
  (t/new-session user))

(defpage [:post "/login"] {:as data}
  (if-let [user (user/retrieve (:email data))]
    (if (crypt/compare (:password data) (:password user))
      (do
        (sess/put! :email (:email user))
        (layouts/flash! (str "Welcome back " (:username user)))
        (response/redirect "/medications"))
      (do 
        (layouts/flash! "Invalid password!")
        (render "/login" {:email (:email data)})))
    (do
      (layouts/flash! "Invalid email")
      (render "/login" {:email (:email data)}))))

(defpage "/signup" {:as user}
  (t/new-user user)
  (println (:headers (request/ring-request))))

(defpage [:post "/signup"] {:as user}
  (println user)
  (let [email (user :email)
        username (user :username)
        password (user :password)
        country (country)
        ip (ip)]
  (if (user/valid? user)
      (do 
        (user/add! {:email email :username username :password password :country country :ip ip})
        (sess/put! :email email)
        (layouts/flash! "Welcome to DocuMeds")
        (response/redirect "/medications")))))

; Log Out ---------------------------------------------------------------------
(defpage "/logout" []
  (sess/remove! :email)
  (layouts/flash! "Logged out successfully!")
  (response/redirect "/medications"))