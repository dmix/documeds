(ns documeds.models.user
  (:require [noir.validation :as validation]
            [noir.util.crypt :as crypt])
  (:use documeds.redis))

; Index ------------------------------------------------------------------------

(defn index []
  @(@r [:smembers (key-users-index)]))

(defn add-index [email]
  (@r [:sadd (key-users-index) email]))

(defn remove-index [email]
  (@r [:srem (key-users-index) email]))

(defn increment []
  @(@r [:incr (key-increment-users)]))

; Getters ------------------------------------------------------------------------

(defn retrieve [email]
  (let [u (apply hash-map @(@r [:hgetall (key-user email)]))]
    (when (not (empty? u))
      {:username (u "username")
       :email (u "email")
       :country (u "country")
       :ip (u "ip")
       :password (u "password")})))

(defn retrieve-multiple [emails]
  (map retrieve emails))

(defn all []  
  (for [email (index)
    :let [user (retrieve email)]]
   user))

(defn group []
  (retrieve-multiple (take 20 (index))))

; Setters ------------------------------------------------------------------------

(defn new-password! [email new-pass]
  (@r [:hset (key-user email) "password" (crypt/encrypt new-pass)]))

(defn add! [u]
  (let [email (:email u)]
    (add-index email) ; Add id to users seq of IDs
    (new-password! email (:password u))
    (@r [:hmset (key-user email) "username" (:username u)
                                 "email" (:email u)
                                 "country" (:country u)
                                 "ip" (:ip u)])))

(defn add-multiple [users]
  (dorun (map add! users)))

; Modify ------------------------------------------------------------------------

(defn update! [user]
  (let [email (user :email)
        password (user :password)]
  (when (not (empty? password))
    (new-password! email password))
  (@r [:hmset (key-user email) "name" (user :username)
                               "email" email])))

(defn remove! [user]
  (let [email (user :email)]
    (remove-index email) ; Remove id to users seq of IDs
    (@r [:del (key-user email)])))

; Validation ------------------------------------------------------------------------

(defn valid? [{:keys [username email password]}]
  (validation/rule (validation/is-email? email)
    [:title "You need to submit a real email address"])
  (validation/rule (validation/min-length? password 3)
    [:title "Your password must have more than 3 letters."])
  (validation/rule (validation/has-value? username)
    [:dosage "You must set a username"])
  (validation/rule (validation/has-value? email)
    [:dosage "You must set an email"])
  (validation/rule (validation/has-value? password)
    [:dosage "You must set a password"])
  (not (validation/errors? :username :email :password)))