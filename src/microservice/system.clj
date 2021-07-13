(ns microservice.system
  (:require [com.stuartsierra.component :as component]
            [microservice.component.datasource :as datasource]
            [microservice.component.handler :as handler]
            [microservice.component.migration :as migration]
            [microservice.component.router :as router]
            [microservice.component.swagger :as swagger]
            [microservice.component.web-server :as web-server]
            [microservice.logging :as logging]
            [microservice.component.proto.impl.date]
            [microservice.component.proto.impl.encoding]
            [microservice.component.proto.impl.pg_json]))

(defn make-system
  "Creates new Sierra Component system map."
  []
  (logging/init-logging)
  (component/system-map
    :wedding.component/datasource (datasource/new-datasource)
    :wedding.component/handler (handler/new-handler :wedding.component/router
                                                    :wedding.component/swagger)
    #_#_:wedding.component/migration (migration/new-migration :wedding.component/datasource)
    :wedding.component/router (router/new-router :wedding.component/datasource
                                                 :wedding.component/swagger)
    :wedding.component/swagger (swagger/new-swagger-handler)
    :wedding.component/web-server (web-server/new-webserver :wedding.component/handler)))
