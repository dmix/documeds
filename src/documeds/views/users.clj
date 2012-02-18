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

(defn flash! [message]
  (sess/flash-put! message)
  nil)

; Authentication --------------------------------------------------------------
(defn force-login []
  (flash! "Please log in to view that page!")
  (response/redirect "/"))

(defmacro login-required [& body]
  `(if-not (sess/get :email)
     (force-login)
     (do ~@body)))

(defn create-user [email password]
  ; (users/set-email! email email)
  ; (users/set-pass! email password)
  ; (sess/put! :email email)
  ; (users/user-get email))
)

(defn check-login [{:keys [email password]}]
  (if (some empty? [email password])
    (flash! "Both fields are required.  This really shouldn't be difficult.")
    (if-not (re-find user/email-regex email)
      (flash! "That's not an email address!")
      (if-let [user (user/retrieve email)]
        (if (crypt/compare password (:pass user))
          (do
            (sess/put! :email email)
            user)
          (flash! "Invalid login!"))
        (create-user email password)))))

(defpage "/login" {:as user}
  (t/new-session user))

(defpage [:post "/login"] {:as user}
  (t/new-session user))

(defpage "/signup" {:as user}
  (t/new-user user))

(defpage [:post "/signup"] {:as user}
  (t/new-user user))
