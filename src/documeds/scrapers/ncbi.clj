(ns documeds.scrapers.ncbi
  (:use [net.cgrand.enlive-html :as html]
        net.cgrand.moustache
        [aleph.redis :only (redis-client)]))

(def letters (map char (concat (range 97 123))))
(def r (delay (redis-client {:host redis-url :password redis-pass :port redis-port})))

(defn info-url [url]
  (str "http://www.ncbi.nlm.nih.gov" url))

(defn list-url [letter] 
  (str "http://www.ncbi.nlm.nih.gov/pubmedhealth/s/drugs_and_supplements/" letter))

(defn fetch-url [url]
 (html/html-resource (java.net.URL. url)))

(defn parse-urls [letter]
  (html/select (fetch-url (list-url (str letter))) [:ul.resultList :li :a]))

(defn store-urls []
  (doseq [letter letters]
    (doseq [item (parse-urls letter)]
      @(@r [:sadd "medication-urls" ((item :attrs) :href)]))))