(ns microservice.system
  (:require [com.stuartsierra.component :as component]
            [microservice.component.audit-logging :as audit-logger]
            [microservice.component.aws-creds-provider :as aws-creds]
            [microservice.component.datasource :as datasource]
            [microservice.component.email-notification-adapter :as email-notification]
            [microservice.component.handler :as handler]
            [microservice.component.migration :as migration]
            [microservice.component.router :as router]
            [microservice.component.swagger :as swagger]
            [microservice.component.web-server :as web-server]
            [microservice.logging :as logging]
            [microservice.component.proto.impl.date]
            [microservice.component.proto.impl.encoding]
            [microservice.component.proto.impl.pg_json]
            [taoensso.timbre :as timbre]))

(defn make-system
  "Creates new Sierra Component system map."
  []
  (logging/init-logging)
  (component/system-map
    :wedding.component/audit-logger (audit-logger/new-audit-logger-impl :wedding.component/datasource)
    :wedding.component/aws-credentials-provider (aws-creds/new-credentials-provider)
    :wedding.component/datasource (datasource/new-datasource)
    :wedding.component/email-notification-adapter (email-notification/new-email-notification-adapter
                                                    :wedding.component/aws-credentials-provider)
    :wedding.component/handler (handler/new-handler :wedding.component/router
                                                    :wedding.component/swagger)
    :wedding.component/migration (migration/new-migration :wedding.component/datasource)
    :wedding.component/router (router/new-router :wedding.component/datasource
                                                 :wedding.component/email-notification-adapter
                                                 :wedding.component/swagger
                                                 :wedding.component/audit-logger)
    :wedding.component/swagger (swagger/new-swagger-handler)
    :wedding.component/web-server (web-server/new-webserver :wedding.component/handler)))

(Thread/setDefaultUncaughtExceptionHandler
  (reify Thread$UncaughtExceptionHandler
    (uncaughtException [_ thread ex]
      (timbre/info "Hello")
      (timbre/error ex "Uncaught exception on" (.getName thread)))))
