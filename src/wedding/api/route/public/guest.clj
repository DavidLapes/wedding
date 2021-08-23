(ns wedding.api.route.public.guest
  (:require [wedding.api.controller.guest :as controller]
            [wedding.api.schema.guest :refer [SubmitRSVP]]
            [wedding.api.schema.message :refer [MessageResponse]]
            [wedding.lib.api.router :refer [wrap-with-context]]))

(def routes
  ["/guests"
   {:swagger {:tags ["Guests"]}}
   ["/rsvp"
    {:post {:summary "Creates RSVP record for a guest"
            :responses {200 {:body SubmitRSVP}}
            :parameters {:body MessageResponse}
            :handler (wrap-with-context
                       (fn [request]
                         (controller/rsvp request)))}}]])
