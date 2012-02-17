(ns documeds.scrapers.ncbi
  (:require [documeds.models.medication :as medication])
  (:use [net.cgrand.enlive-html :as html]
        net.cgrand.moustache
        documeds.redis))

(def letters (map char (concat (range 97 123))))

; Fetch NCBI URLS ------------------------------------------------------------------------

(defn list-url [letter]
  (str "http://www.ncbi.nlm.nih.gov/pubmedhealth/s/drugs_and_supplements/" letter))

(defn fetch-url [url]
 (html/html-resource (java.net.URL. url)))

(defn parse-urls [letter]
  (let [url-data (fetch-url (list-url (str letter)))]
    (html/select url-data [:ul.resultList :li :a])))

(defn store-urls []
  (doseq [letter letters]
    (doseq [item (parse-urls letter)]
      (println (str "Storing URL for " ((item :attrs) :href)))
      @(@r [:sadd "medication-urls" ((item :attrs) :href)]))))

(defn urls-index []
  @(@r [:smembers "medication-urls"]))

; Fetch Medication Info ------------------------------------------------------------------------

(defn info-url [url]
  (str "http://www.ncbi.nlm.nih.gov" url))

(defn content-html [id, url-data, attribute]
  (first (html/select url-data [(keyword (str "#" id attribute))])))

(defn parse-section [id, url-data, attribute]
  (let [elements (content-html id url-data attribute)
        content (apply str (html/emit* [elements]))]
    (if (= content " ")
      "empty row"
      content)))

; (set-info (parse-info "http://www.ncbi.nlm.nih.gov/pubmedhealth/PMH0000928/"))
(defn parse-info [url]
  (let [url-data (fetch-url (info-url url))
        med-name (first (:content (first (html/select url-data [:h1 :.title]))))
        sub-name (first (:content (first (html/select url-data [:h1 :.title_addtn]))))
        id (first (clojure.string/split (:id (:attrs (first (html/select url-data [:.body-content :.section])))) #"-"))]
  { :name med-name
    :subtitle sub-name
    :why (parse-section id url-data "-why")
    :how (parse-section id url-data "-how")
    :side_effects (parse-section id url-data "-sideEffects")
    :other_uses (parse-section id url-data "-otherUses")
    :other_information (parse-section id url-data "-otherInformation")
    :precautions (parse-section id url-data "-precautions")
    :dietary (parse-section id url-data "-specialDietary")
    :brand_names (parse-section id url-data "-brandNames")
    ; :brand_names_combo (parse-section id url-data "-brandNamesCombo")
    :overdose (parse-section id url-data "-overdose")
    :if_i_forget (parse-section id url-data "-ifIForget")
    :slug (clojure.string/replace (clojure.string/replace (clojure.string/lower-case med-name) #"[^0-9a-z ]/i" "") " " "_")
  }))

(defn set-info [med]
  (medication/add! med))

(defn store-info []
  (doseq [url (urls-index)]
    (println (str "Fetching info from " url))
    (set-info (parse-info url))))

(defn -main [& args]
  (println "Running scrapers")
  (store-urls)
  (store-info))
