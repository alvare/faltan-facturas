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
            [clj-time.periodic :as periodic]
            [clj-json.core :as json]))

(def formatd (format/formatters :year-month-day))

(def arbitrary-jueves (time/date-time 2013 4 25))

(def jueveses (partition (count (models/all)) (take 1000 (periodic/periodic-seq arbitrary-jueves (time/days 7)))))

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defn rndm [seed max]
  (let [r (java.util.Random. seed)]
    (.nextInt r max)))

(defn ppl-sort [pos coll]
  (sort-by first (map-indexed
    (fn [idx item] [(rndm (inc idx) (+ 10000 pos)) item]) coll)))

(defn to-date [str]
  (format/parse formatd str))

(defn from-date [date]
  (format/unparse formatd date))

(defn get-jueveses [whend]
  (first (filter (fn [cluster]
    (some (fn [date]
      (= whend date)) cluster)) jueveses)))

(defn choosen [date]
  (let [jvs (get-jueveses date)]
    (nth (ppl-sort (.indexOf jvs jueveses) (models/all)) (.indexOf jvs date))))

(defn facturas [req]
  (json-response (choosen (to-date ((req :query-params) "date")))))

(defn view [req]
  (response/render (slurp (io/resource "templates/facturas.html")) req))

(defn index [req]
  (response/render (clostache/render (slurp (io/resource "templates/index.html")) (zipmap [:year :month :day :hour :minutes] (split ((req :query-params) "date") #"-"))) req))

(defroutes app
  (GET "/" [] index)
  (GET "/view" [] view)
  (GET "/facturas" [] facturas)
  (route/resources "/" {:root "templates"})
  (route/not-found (slurp (io/resource "404.html"))))
