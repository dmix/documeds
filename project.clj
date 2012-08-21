(defproject documeds "0.1.0-SNAPSHOT"
            :description "Medication data engine"
            :dependencies [[org.clojure/clojure "1.4.0"]
                           [noir "1.2.1"]
                           [enlive "1.0.1"]
                           [aleph "0.3.0-alpha3"]
                           [net.cgrand/moustache "1.1.0"]
                           [cheshire "4.0.1"]]
            :main documeds.server)