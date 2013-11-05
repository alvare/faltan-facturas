(defproject faltan "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://faltan.herokuapp.com"
  :license {:name "FIXME: choose"
            :url "http://example.com/FIXME"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.cemerick/friend "0.2.0"]
                 [compojure "1.1.5" :exclusions [ring/ring-core]]
                 [ring/ring-jetty-adapter "1.1.0"]
                 [ring/ring-devel "1.1.0"]
                 [environ "0.2.1"]
                 [hiccup "1.0.4"]
                 [org.clojure/java.jdbc "0.2.3"]
                 [postgresql "9.1-901.jdbc4"]
                 [clj-time "0.5.0"]
                 [clj-json "0.3.2"]]
  :min-lein-version "2.0.0"
  :plugins [[environ/environ.lein "0.2.1"]]
  :hooks [environ.leiningen.hooks]
  :profiles {:production {:env {:production true}}})
