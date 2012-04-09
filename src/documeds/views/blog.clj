(ns documeds.views.blog
  (:require [documeds.templates.layouts :as layouts]
            [documeds.templates.blogs :as t]
            [noir.response :as response]
            [noir.request :as request])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers
        hiccup.form-helpers))

(defpage "/blog" []
  (t/posts))