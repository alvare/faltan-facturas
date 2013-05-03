(ns faltan.models
  (:require [clojure.java.jdbc :as sql]))

(println (System/getenv "DATABASE_URL"))

(defn all []
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/with-query-results results
      ["select * from members order by id desc"]
      (into [] results))))

;(defn create [shout]
;  (sql/with-connection (System/getenv "DATABASE_URL")
;    (sql/insert-values :shouts [:body] [shout])))
