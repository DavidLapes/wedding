(ns wedding.api.route.private.room
  (:refer-clojure :exclude [update])
  (:require [schema.core :as s]
            [wedding.api.controller.room :as controller]
            [wedding.api.schema.message :refer [MessageResponse]]
            [wedding.api.schema.room :refer [CreateRoom RoomOutput UpdateRoom RoomListOutput]]
            [wedding.lib.api.router :refer [wrap-with-context]]))

(def routes
  ["/rooms"
   {:swagger {:tags ["Rooms"]}}
   [""
    {:get  {:summary "Returns list of rooms"
            :responses {200 {:body RoomListOutput}}
            :handler (wrap-with-context
                       (fn [request]
                         (controller/get request)))}
     :post {:summary "Creates new room"
            :responses {201 {:body RoomOutput}}
            :parameters {:body CreateRoom}
            :handler (wrap-with-context
                       (fn [request]
                         (controller/create request)))}}]
   ["/:id"
    {:get    {:summary    "Returns room by id"
              :responses  {200 {:body RoomOutput}}
              :parameters {:path {:id s/Int}}
              :handler    (wrap-with-context
                            (fn [request]
                              (controller/get-by-id request)))}
     :put    {:summary    "Updates existing room"
              :responses  {200 {:body RoomOutput}}
              :parameters {:body UpdateRoom
                           :path {:id s/Int}}
              :handler    (wrap-with-context
                            (fn [request]
                              (controller/update request)))}
     :delete {:summary    "Deletes room by id"
              :responses  {200 {:body MessageResponse}}
              :parameters {:path {:id s/Int}}
              :handler    (wrap-with-context
                            (fn [request]
                              (controller/delete request)))}}]
   ["/statistics/available"
    {:get {:summary "Returns list of available rooms"
           :responses {200 {:body RoomListOutput}}
           :handler (wrap-with-context
                      (fn [request]
                        (controller/get-all-available request)))}}]])
