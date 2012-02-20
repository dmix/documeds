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

; (set-info (parse-info "http://www.ncbi.nlm.nih.gov/pubmedhealth/PMH0000928/"))

(def attributes { :why "-why"
                  :how "-how"
                  :side_effects "-sideEffects"
                  :other_uses "-otherUses"
                  :other_information "-otherInformation"
                  :precautions "-precautions"
                  :dietary "-specialDietary"
                  :brand_names "-brandNames"
                  :brand_names_combo "-brandNamesCombo"
                  :overdose "-overdose"
                  :if_i_forget "-ifIForget"})

(defn id-of-sections [url-data]
  (first (clojure.string/split
      (:id (:attrs (first
          (html/select url-data [:.body-content :.section])))) #"-")))

(defn parse-name [url-data attribute]
  (first (:content (first (html/select url-data [:h1 attribute])))))

(defn parse-section [id url-data attribute]
  (let [elements (content-html id url-data attribute)
        content (apply str (html/emit* [elements]))]
    (if (= content " ")
      nil
      content)))

; (parse-info "/pubmedhealth/PMH0000928/")
(defn parse-info [url]
  (let [url-data (fetch-url (info-url url))
        med-name (parse-name url-data :.title)
        sub-name (parse-name url-data :.title_addtn)
        base { :name med-name :subtitle sub-name }
        id (id-of-sections url-data)]
    (merge base
      (into {} 
        (map (fn [attribute]
          (let [parsed (parse-section id url-data (last attribute))]
            (if-not (= parsed "")
              {(first attribute) parsed}))) 
          attributes)))))

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
