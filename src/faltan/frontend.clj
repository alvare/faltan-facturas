(ns faltan.frontend
  (:require [faltan.models :as models]
            [clj-time.core :as time]
            [clj-time.format :as format]
            [clj-time.periodic :as periodic]))

(def formatd (format/formatters :year-month-day))

(def arbitrary-jueves (time/date-time 2013 4 25))

(defn jueveses [] (partition (count (models/all)) (take 1000 (periodic/periodic-seq arbitrary-jueves (time/days 7)))))

(defn rndm [seed max]
  (let [r (java.util.Random. seed)]
    (.nextInt r max)))

(defn ppl-sort [pos coll]
  (sort-by first (map-indexed
    (fn [idx item] [(rndm (inc idx) (+ 10000 pos)) item]) coll)))

(defn to-date [text]
  (format/parse formatd text))

(defn from-date [date]
  (format/unparse formatd date))

(defn get-jueveses [whend]
  (first (filter (fn [cluster]
    (some (fn [date]
      (= whend date)) cluster)) (jueveses))))

;[8774,{"picture":nil,"name":"facundo","id":2}]
(defn choosen [date]
  (let [jvs (get-jueveses date)]
    (nth (ppl-sort (.indexOf jvs (jueveses)) (models/all)) (.indexOf jvs date))))
