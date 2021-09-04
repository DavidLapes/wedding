(ns wedding.api.route.public.guest
  (:require [schema.core :as s]
            [wedding.api.controller.guest :as controller]
            [wedding.api.schema.guest :refer [SubmitRSVP PublicGuestListOutput]]
            [wedding.api.schema.message :refer [MessageResponse]]
            [wedding.lib.api.router :refer [wrap-with-context]]))

(def routes
  ["/guests"
   {:swagger {:tags ["Guests"]}}
   [""
    {:get {:summary "Returns all guests who have not answered RSVP form yet"
           :responses {200 {:body PublicGuestListOutput}}
           :handler (wrap-with-context
                      (fn [request]
                        (controller/get-rsvp-guests request)))}}]
   ["/rsvp"
    {:post {:summary "Creates RSVP record for a guest"
            :responses {200 {:body MessageResponse}}
            :parameters {:body SubmitRSVP
                         :path {:id s/Int}}
            :handler (wrap-with-context
                       (fn [request]
                         (controller/rsvp request)))}}]])
