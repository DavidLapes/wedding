(ns microservice.component.router
  (:require [com.stuartsierra.component :as component]
            [muuntaja.core :as m]
            [reitit.coercion.spec :as reitit-spec]
            [reitit.ring :as ring]
            [reitit.ring.middleware.parameters :as parameters]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.exception :as exception]
            [reitit.ring.coercion :as coercion]
            [ring.logger :as logger]
            [taoensso.timbre :as timbre]
            [wedding.api.route.health-check :as health-check]
            [wedding.api.route.public.auth :as auth]
            [wedding.auth.middleware :as authentication]))

(defrecord Router [datasource swagger]
  component/Lifecycle

  (start [this]
    (timbre/info "Starting Router component")
    (let [router (ring/router
                   [(:swagger-routes swagger)
                    health-check/routes

                    ["/api"

                     ["/public"
                      auth/routes]

                     ["/private"
                      {:middleware [authentication/wrap-with-jwt-middleware]}]]]

                   {:data {:coercion   reitit-spec/coercion
                           :ctx        {:datasource (:datasource datasource)}
                           :muuntaja   m/instance
                           :middleware [;; ring handler logger
                                        logger/wrap-with-logger
                                        ;; query-params & form-params
                                        parameters/parameters-middleware
                                        ;; content-negotiation
                                        muuntaja/format-negotiate-middleware
                                        ;; encoding response body
                                        muuntaja/format-response-middleware
                                        ;; exception handling
                                        ;;TODO Error & Exception handling
                                        ;;exception/exception-middleware
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
  [datasource-ref swagger-ref]
  (component/using
    (map->Router {})
    {:datasource datasource-ref
     :swagger    swagger-ref}))
