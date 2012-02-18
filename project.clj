(defproject documeds "0.1.0-SNAPSHOT"
            :description "Medication data engine"
            :dependencies [[org.clojure/clojure "1.3.0"]
                           [noir "1.2.0"]
                           [enlive "1.0.0"]
                           [aleph "0.2.1-alpha2-SNAPSHOT"]
                           [net.cgrand/moustache "1.0.0"]
                           [cheshire "2.2.0"]]
            :main documeds.server)