(ns faltan.controllers
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.route :as route]
            [clojure.string :refer [split]]
            [clojure.java.io :as io]
            [ring.util.response :as ring]
            [clj-json.core :as json]
            [faltan.models :as models]
            [faltan.templates :as templates]
            [faltan.frontend :refer [choosen to-date]]))

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defn index [req]
  (templates/index {}))

(defn facturas [req]
  (json-response (choosen (to-date ((req :query-params) "date")))))

(defn toca [date]
  (templates/index (merge (second (choosen (to-date date))) {:date date})))

(defn backend-index [req]
  (templates/backend-index {}))

(defroutes app
  (GET "/" [] index)
  (GET "/facturas" [] facturas)
  (GET "/toca/:d" [d] (toca d))
  (GET "/backend" [] backend-index)
  (route/resources "/" {:root "templates"})
  (route/not-found (slurp (io/resource "404.html"))))
