(ns microservice.component.router
  (:require [com.stuartsierra.component :as component]
            [muuntaja.core :as m]
            [reitit.coercion.spec :as reitit-spec]
            [reitit.ring :as ring]
            [reitit.ring.middleware.parameters :as parameters]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.exception :as exception]
            [reitit.ring.coercion :as coercion]
            [taoensso.timbre :as timbre]
            [wedding.api.route.public.health-check :as health-check]))

(defrecord Router [swagger]
  component/Lifecycle

  (start [this]
    (timbre/info "Starting Router component")
    (let [swagger-routes (:swagger-routes swagger)
          swagger-routes (if (some? swagger-routes)
                           [swagger-routes]
                           [])
          routes ["/api"
                  ["/public"
                   health-check/routes]]
          router (ring/router
                   (conj swagger-routes routes)
                   {:data {:coercion   reitit-spec/coercion
                           :ctx        {}
                           :muuntaja   m/instance
                           :middleware [;; query-params & form-params
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
  ([]
   (map->Router {}))
  ([swagger-ref]
   (component/using
     (map->Router {})
     {:swagger     swagger-ref})))
