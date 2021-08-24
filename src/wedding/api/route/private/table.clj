(ns wedding.api.route.private.table
  (:refer-clojure :exclude [update])
  (:require [schema.core :as s]
            [wedding.api.controller.table :as controller]
            [wedding.api.schema.message :refer [MessageResponse]]
            [wedding.api.schema.table :refer [CreateTable UpdateTable TableOutput TableListOutput]]
            [wedding.lib.api.router :refer [wrap-with-context]]))

(def routes
  ["/tables"
   {:swagger {:tags ["Tables"]}}
   [""
    {:get  {:summary "Returns list of tables"
            :responses {200 {:body TableListOutput}}
            :handler (wrap-with-context
                       (fn [request]
                         (controller/get request)))}
     :post {:summary "Creates new table"
            :responses {201 {:body TableOutput}}
            :parameters {:body CreateTable}
            :handler (wrap-with-context
                       (fn [request]
                         (controller/create request)))}}]
   ["/:id"
    {:get    {:summary    "Returns table by id"
              :responses  {200 {:body TableOutput}}
              :parameters {:path {:id s/Int}}
              :handler    (wrap-with-context
                            (fn [request]
                              (controller/get-by-id request)))}
     :put    {:summary    "Updates existing table"
              :responses  {200 {:body TableOutput}}
              :parameters {:body UpdateTable
                           :path {:id s/Int}}
              :handler    (wrap-with-context
                            (fn [request]
                              (controller/update request)))}
     :delete {:summary    "Deletes table by id"
              :responses  {200 {:body MessageResponse}}
              :parameters {:path {:id s/Int}}
              :handler    (wrap-with-context
                            (fn [request]
                              (controller/delete request)))}}]])
