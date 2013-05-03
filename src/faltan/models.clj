(ns faltan.models
  (:require [clojure.java.jdbc :as sql]))

(let [db-host "localhost"
      db-port 5432
      db-name "facturas"]
 
  (def db {:classname "org.postgresql.Driver"
           :subprotocol "postgresql"
           :subname (str "//" db-host ":" db-port "/" db-name)
           :user "postgres"
           :password "chango"}))

(println (System/getenv))

(defn all []
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/with-query-results results
      ["select * from members order by id desc"]
      (into [] results))))

;(defn create [shout]
;  (sql/with-connection (System/getenv "DATABASE_URL")
;    (sql/insert-values :shouts [:body] [shout])))
