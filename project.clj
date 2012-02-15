(defproject documeds "0.1.0-SNAPSHOT"
            :description "Medication data engine"
            :dependencies [[org.clojure/clojure "1.2.1"]
                           [org.clojure/clojure-contrib "1.2.0"]
                           [noir "1.2.0"]
                           [enlive "1.0.0"]
                           [aleph "0.2.0"]
                           [net.cgrand/moustache "1.0.0"]]
            :main documeds.server)