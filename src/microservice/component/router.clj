(ns microservice.component.router
  (:require [com.stuartsierra.component :as component]
            [microservice.component.middleware.auth :as auth]
            [microservice.component.middleware.cors :as cors]
            [microservice.component.middleware.exception :as exception]
            [muuntaja.core :as m]
            [reitit.coercion.schema :as reitit-schema]
            [reitit.ring :as ring]
            [reitit.ring.middleware.parameters :as parameters]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.coercion :as coercion]
            [ring.logger :as logger]
            [taoensso.timbre :as timbre]
            [wedding.api.route.health-check :as health-check]
            [wedding.api.route.private.audit :as audit-private]
            [wedding.api.route.private.guest :as guest-private]
            [wedding.api.route.private.room :as room-private]
            [wedding.api.route.private.statistics :as statistics-private]
            [wedding.api.route.private.table :as table-private]
            [wedding.api.route.public.auth :as auth-public]
            [wedding.api.route.public.guest :as guest-public]
            [wedding.auth.middleware :as authentication]))

(defrecord Router [email-notification-adapter datasource swagger audit-logger]
  component/Lifecycle

  (start [this]
    (timbre/info "Starting Router component")
    (let [datasource (:datasource datasource)
          router (ring/router
                   [(:swagger-routes swagger)
                    health-check/routes

                    ["/api"

                     ["/public"
                      auth-public/routes
                      guest-public/routes]

                     ["/private"
                      {:middleware [(partial authentication/wrap-with-jwt-middleware datasource)
                                    auth/wrap-authentication-check]}
                      audit-private/routes
                      guest-private/routes
                      room-private/routes
                      statistics-private/routes
                      table-private/routes]]]

                   {:data {:coercion   reitit-schema/coercion
                           :ctx        {:datasource datasource
                                        :notification-adapter {:email email-notification-adapter}
                                        :audit-logger audit-logger}
                           :muuntaja   m/instance
                           :middleware [;; ring handler logger
                                        logger/wrap-with-logger
                                        ;; ring cors middleware
                                        cors/cors-middleware
                                        ;; query-params & form-params
                                        parameters/parameters-middleware
                                        ;; content-negotiation
                                        muuntaja/format-negotiate-middleware
                                        ;; encoding response body
                                        muuntaja/format-response-middleware
                                        ;; exception handling
                                        exception/exception-middleware
                                        ;; decoding request body
                                        muuntaja/format-request-middleware
                                        ;; coercing response body
                                        coercion/coerce-response-middleware
                                        ;; coercing request parameters
                                        coercion/coerce-request-middleware]}})]
      (timbre/info "Started Router component")
      (assoc this :router router)))

  (stop [this]
    (timbre/info "Stopping Router component")
    (timbre/info "Stopped Router component")
    (dissoc this :router)))

(defn new-router
  "Returns instance of Router component."
  [datasource-ref email-notification-adapter-ref swagger-ref audit-logger-ref]
  (component/using
    (map->Router {})
    {:datasource                  datasource-ref
     :email-notification-adapter  email-notification-adapter-ref
     :swagger                     swagger-ref
     :audit-logger                audit-logger-ref}))
