(ns faltan.web
  (:require [compojure.handler :refer [site]]
            [clojure.java.io :as io]
            [ring.middleware.stacktrace :as trace]
            [ring.middleware.session.cookie :as cookie]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :as reload]
            [clojure.string :refer [split]]
            [environ.core :refer [env]]
            [faltan.controllers :as controllers]))

(defn wrap-error-page [handler]
  (fn [req]
    (try (handler req)
         (catch Exception e
           {:status 500
            :headers {"Content-Type" "text/html"}
            :body (slurp (io/resource "500.html"))}))))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))
        store (cookie/cookie-store {:key (env :session-secret)})]
    (jetty/run-jetty (-> #'controllers/app
                         ((if (env :production)
                            wrap-error-page
                            reload/wrap-reload))
                         (site {:session {:store store}}))
                     {:port port :join? false})))
