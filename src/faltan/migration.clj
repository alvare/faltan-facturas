(ns faltan.migration
  (:require [clojure.java.jdbc :as sql]
            [faltan.models :as models]))

(defn create-tables []
  (sql/with-connection models/db
    (sql/create-table :members
      [:id :serial "PRIMARY KEY"]
      [:name :varchar "NOT NULL"]
      [:picture :text])))

(defn insert-members []
  (sql/with-connection models/db
    (sql/insert-records :members
      {:name "Facundo"}
      {:name "Rasta"}
      {:name "Ezequiel"}
      {:name "Ale"})))

(defn -main []
  (print "Creating database structure...") (flush)
  (create-tables)
  (println " done"))
