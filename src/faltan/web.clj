(ns faltan.web
  (:require [clojure.java.io :as io]
            [ring.middleware.stacktrace :as trace]
            [ring.middleware.session.cookie :as cookie]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :as reload]
            [environ.core :refer [env]]
            [faltan.controllers :refer [app]]))

(defn wrap-error-page [handler]
  (fn [req]
    (try (handler req)
         (catch Exception e
           {:status 500
            :headers {"Content-Type" "text/html"}
            :body (slurp (io/resource "500.html"))}))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (-> #'app
                         ((if (env :production)
                            wrap-error-page
                            reload/wrap-reload)))
                     {:port port :join? false})))
