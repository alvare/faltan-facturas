(ns faltan.controllers
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [clojure.string :refer [split]]
            [clojure.java.io :as io]
            [ring.util.response :as ring]
            [clj-json.core :as json]
            [faltan.models :as models]
            [ring.util.response :as resp]
            [faltan.templates :as templates]
            [faltan.frontend :refer [choosen to-date]]
            [cemerick.friend :as friend]
            [cemerick.friend.workflows :as workflows]
            [cemerick.friend.credentials :as credentials]))

(def users {"alvare" {:username "alvare"
                      :password (credentials/hash-bcrypt "chango")
                      :roles #{::user}}})

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

(defn login [req]
  (templates/backend-login))

(defn backend-index [req]
  (templates/backend-index (models/all)))

(defroutes app-routes
  (GET "/" [] index)
  (GET "/facturas" [] facturas)
  (GET "/toca/:d" [d] (toca d))
  (GET "/backend" [] (friend/authorize #{::user} backend-index))
  (GET "/login" [] login)
  (GET "/logout" [] (friend/logout* (resp/redirect "/login")))
  (route/resources "/" {:root "templates"})
  (route/not-found (slurp (io/resource "404.html"))))

(def app
  (handler/site
   (friend/authenticate app-routes
     {:allow-anon? true
      :login-uri "/login"
      :default-landing-uri "/backend"
      :credential-fn (partial credentials/bcrypt-credential-fn users)
      :workflows [(workflows/interactive-form)]})))
