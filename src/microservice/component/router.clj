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
            [wedding.api.route.public.health-check :as health-check]
            [wedding.auth.middleware :as authentication]))

(defrecord Router [swagger]
  component/Lifecycle

  (start [this]
    (timbre/info "Starting Router component")
    (let [router (ring/router
                   [(:swagger-routes swagger)
                    ["/api"
                     ["/public"
                      health-check/routes]]]
                   {:data {:coercion   reitit-spec/coercion
                           :ctx        {}
                           :muuntaja   m/instance
                           :middleware [;; ring handler logger
                                        logger/wrap-with-logger
                                        ;; buddy authentication
                                        authentication/wrap-with-jwt-middleware
                                        ;; query-params & form-params
                                        parameters/parameters-middleware
                                        ;; content-negotiation
                                        muuntaja/format-negotiate-middleware
                                        ;; encoding response body
                                        muuntaja/format-response-middleware
                                        ;; exception handling
                                        ;;TODO Error & Exception handling
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
  [swagger-ref]
  (component/using
    (map->Router {})
    {:swagger swagger-ref}))
