(ns wedding.api.route.private.guest
  (:refer-clojure :exclude [update])
  (:require [schema.core :as s]
            [wedding.api.controller.guest :as controller]
            [wedding.api.schema.guest :refer [CreateGuest UpdateGuest GuestOutput GuestListOutput]]
            [wedding.lib.api.router :refer [wrap-with-context]]))

(def routes
  ["/guests"
   {:swagger {:tags ["Guests"]}}
   [""
    {:get  {:summary "Returns list of guests"
            :responses {200 {:body GuestListOutput}}
            :handler (wrap-with-context
                       (fn [request]
                         (controller/get request)))}
     :post {:summary "Creates new guest"
            :responses {201 {:body GuestOutput}}
            :parameters {:body CreateGuest}
            :handler (wrap-with-context
                       (fn [request]
                         (controller/create request)))}}]
   ["/:id"
    {:get  {:summary "Returns guest by id"
            :responses {200 {:body GuestOutput}}
            :handler (wrap-with-context
                       (fn [request]
                         (controller/get-by-id request)))}
     :put  {:summary "Updates existing guest"
            :responses {200 {:body GuestOutput}}
            :parameters {:body UpdateGuest
                         :path {:id s/Int}}
            :handler (wrap-with-context
                       (fn [request]
                         (controller/update request)))}}]])
