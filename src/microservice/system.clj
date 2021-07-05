(ns microservice.system
  (:require [com.stuartsierra.component :as component]
            [microservice.component.handler :as handler]
            [microservice.component.router :as router]
            [microservice.component.swagger :as swagger]
            [microservice.component.web-server :as web-server]
            [microservice.logging :as logging]))

(defn make-system
  "Creates new Sierra Component system map."
  ([]
   (make-system {}))
  ([{:keys [dev?] :or {dev? false}}]
   (logging/init-logging)
   (component/system-map
     :wedding.component/handler    (if-not dev?
                                     (handler/new-handler :wedding.component/router)
                                     (handler/new-handler :wedding.component/router
                                                          :wedding.component/swagger))
     :wedding.component/router     (if-not dev?
                                     (router/new-router)
                                     (router/new-router :wedding.component/swagger))
     :wedding.component/web-server (web-server/new-webserver :wedding.component/handler))))

(defn make-dev-system
  "Creates new Sierra Component system map for dev purposes."
  ([]
   (make-dev-system {:dev? true}))
  ([{:keys [dev?] :or {dev? true} :as args}]
   (-> (make-system args)
       (merge {:wedding.component/swagger (swagger/new-swagger-handler)}))))
