(ns faltan.templates
  (:use [hiccup.core]
        [hiccup.page]
        [hiccup.form]))

(defn main-template [contents]
  (html5
    [:head 
      (include-css "/css/redmond/jquery-ui-1.9.2.custom.min.css")
      (include-css "/css/style.css")
      (include-js "/js/jquery-2.0.0.min.js")
      (include-js "/js/jquery-ui-1.9.2.custom.min.js")
      (include-js "/js/main.js")]
    [:body contents]))

(defn index [data]
  (main-template
    (html [:div#content
            [:div.centered
              [:div#date]
              [:div#result (:name data)]]])))

(defn backend-main-template [contents]
  (html5
    [:head 
      (include-css "//netdna.bootstrapcdn.com/bootstrap/3.0.1/css/bootstrap.min.css")
      (include-js "/js/jquery-2.0.0.min.js")
      (include-js "/js/main-backend.js")]
    [:body 
      [:div.container
        [:div.page-header
          [:h1 "Facturas Backend"]]
        contents]]))

(defn backend-login []
  (backend-main-template
    (html
      [:form.form-horizontal {:role "form" :action "/login" :method "POST"}
        [:div.form-group
          [:label.col-sm-2.control-label {:for "username"} "User"]
          [:div.col-sm-10
            [:input#username.form-control {:type "text" :placeholder "User" :name "username"}]]]
        [:div.form-group
          [:label.col-sm-2.control-label {:for "password"} "Password"]
          [:div.col-sm-10
            [:input#password.form-control {:type "password" :placeholder "Password" :name "password"}]]]
        [:div.form-group
          [:div.col-sm-10.col-sm-offset-2
            [:button.btn.btn-default {:type "submit" :placeholder "Password"} "Login"]]]])))

(defn backend-index [ppl]
  (backend-main-template
    (html
      [:table.table
        [:thead
          [:tr
            [:th "People"]
            [:th "Action"]]]
        (into [:tbody] (for [person ppl]
          [:tr
            [:td (:name person)]
            [:td [:button.btn.btn-default.borrar {:data-id (:id person)} "Borrar"]]]))])))
