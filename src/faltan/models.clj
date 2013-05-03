(ns faltan.models
  (:require [clojure.java.jdbc :as sql]))

(def db "postgres://nmqvybynywbhtv:BTheho_c3ywg5ZSV6fTrTGMwNc@ec2-54-243-229-57.compute-1.amazonaws.com:5432/d4pll0n9j96s1q")

(defn all []
  (sql/with-connection db
    (sql/with-query-results results
      ["select * from members order by id desc"]
      (into [] results))))

;(defn create [shout]
;  (sql/with-connection (System/getenv "DATABASE_URL")
;    (sql/insert-values :shouts [:body] [shout])))
