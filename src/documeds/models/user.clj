(ns documeds.models.user
  (:require [noir.validation :as validation]
            [noir.util.crypt :as crypt])
  (:use documeds.redis))

; Index ------------------------------------------------------------------------

(defn index []
  @(@r [:smembers (key-users-index)]))

(defn add-index [id]
  (@r [:sadd (key-users-index) id]))

(defn remove-index [id]
  (@r [:srem (key-users-index) id]))

(defn increment []
  @(@r [:incr (key-increment-users)]))

; Getters ------------------------------------------------------------------------

(defn retrieve [id]
  (let [u (apply hash-map @(@r [:hgetall (key-user id)]))]
    (when (not (empty? u))
      {:id id
       :username (u "username")
       :email (u "email")
       :country (u "country")
       :ip (u "ip")})))

(defn retrieve-multiple [ids]
  (map retrieve ids))

(defn all []  
  (for [id (index)
    :let [user (retrieve id)]]
   user))

(defn group []
  (retrieve-multiple (take 20 (index))))

; Setters ------------------------------------------------------------------------

(defn add! [u]
  (let [id (increment)]
    (add-index id) ; Add id to users seq of IDs
    @(@r [:hmset (key-user id) "id" id 
                               "name" (:name u)
                               "username" (:username u)
                               "email" (:email u)
                               "country" (:country u)
                               "ip" (:ip u)])))

(defn new-password! [id new-pass]
 @(r [:hset (key-user id) "pass" (crypt/encrypt new-pass)]))

(defn add-multiple [users]
  (dorun (map add! users)))

; Modify ------------------------------------------------------------------------

(defn update! [user]
  (let [id (user :id)
        password (user :password)]
  (when (not (empty? password))
    (new-password! id password))
  (@r [:hmset (key-user id) "id" id
                            "name" (user :name)
                            "email" (user :email)])))

(defn remove! [user]
  (let [id (user :id)]
    (remove-index id) ; Remove id to users seq of IDs
    (@r [:del (key-user id)])))

; Validation ------------------------------------------------------------------------

(def email-regex #"[a-zA-Z0-9._+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}")

(defn valid? [{:keys [title dosage]}]
  (validation/rule (validation/min-length? title 4)
      [:title "Your title must have more than 5 letters."])
  (validation/rule (validation/has-value? dosage)
      [:dosage "You must set a dosage"])
  (not (validation/errors? :title :dosage)))
