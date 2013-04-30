(ns faltan.controllers
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [clojure.string :as str]
            [compojure.route :as route]
            [compojure.response :as response]
            [clostache.parser :as clostache]
            [clojure.string :refer [split]]
            [clojure.java.io :as io]
            [faltan.models :as models]
            [ring.util.response :as ring]
            [clj-time.core :as time]
            [clj-time.format :as format]
            [clj-time.periodic :as periodic]))

(def format (format/formatters :year-month-day))
(def arbitrary-jueves (time/date-time 2013 4 25))

(defn to-date [str]
  (format/parse format str))

(defn from-date [date]
  (format/unparse format date))

(defn jueveses [people]
  (partition people (take 1000 (periodic/periodic-seq arbitrary-jueves (time/days 7)))))

(defn get-jueveses [when count]
  (filter (fn [cluster]
    (some (fn [date]
      (= when date)) cluster)) (jueveses count)))

(defn facturas [req]
  (response/render (from-date (get (get-jueveses (to-date ((req :query-params) "date")) 4) 0)) req))

(defn index [req]
  (response/render (clostache/render (slurp (io/resource "templates/index.html")) (zipmap [:year :month :day :hour :minutes] (split ((req :query-params) "date") #"-"))) req))

(defroutes app
  (GET "/" [] index)
  (GET "/facturas" [] facturas)
  (route/resources "/" {:root "templates"})
  (route/not-found (slurp (io/resource "404.html"))))
