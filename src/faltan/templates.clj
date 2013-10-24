(ns faltan.templates
  (:use [hiccup.core]
        [hiccup.page]))

(defn body [contents]
  (html5
    [:head 
      (include-css "/css/redmond/jquery-ui-1.9.2.custom.min.css")
      (include-css "/css/style.css")
      (include-js "/js/jquery-2.0.0.min.js")
      (include-js "/js/jquery-ui-1.9.2.custom.min.js")
      (include-js "/js/main.js")]
    [:body contents]))

(defn index [data]
  (body
    (html [:div#content
            [:div.centered
              [:div#date]
              [:div#result (:name data)]]])))

(defn backend-index [data]
  (str "ok"))
