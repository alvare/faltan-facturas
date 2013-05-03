(ns faltan.migration
  (:require [clojure.java.jdbc :as sql]
            [faltan.models :as models]))

(defn create-tables []
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/create-table :members
      [:id :serial "PRIMARY KEY"]
      [:name :varchar "NOT NULL"]
      [:picture :text])))

(defn -main []
  (print "Creating database structure...") (flush)
  (create-tables)
  (println " done"))
