(ns wedding.api.route.private.statistics
  (:require [wedding.api.controller.statistics :as controller]
            [wedding.api.schema.statistics :refer [BundledStatisticsOutput RSVPAnsweredOutput RSVPUnansweredOutput AccommodationAcceptedOutput AccommodationDeclinedOutput]]
            [wedding.lib.api.router :refer [wrap-with-context]]))

(def routes
  ["/statistics"
   {:swagger {:tags ["Statistics"]}}
   [""
    {:get {:summary "Returns grouped statistics for RSVP and accommodation"
           :responses {200 {:body BundledStatisticsOutput}}
           :handler (wrap-with-context
                      (fn [request]
                        (controller/bundled-statistics request)))}}]
   ["/rsvpunanswered"
    {:get {:summary "Returns number of guests who haven't answered RSVP yet"
           :responses {200 {:body RSVPUnansweredOutput}}
           :handler (wrap-with-context
                      (fn [request]
                        (controller/rsvp-unanswered request)))}}]
   ["/rsvpanswered"
    {:get {:summary "Returns number of guests who have already answered RSVP"
           :responses {200 {:body RSVPAnsweredOutput}}
           :handler (wrap-with-context
                      (fn [request]
                        (controller/rsvp-answered request)))}}]
   ["/accommodationdeclined"
    {:get {:summary "Returns number of guests who don't want the accommodation at the wedding"
           :responses {200 {:body AccommodationDeclinedOutput}}
           :handler (wrap-with-context
                      (fn [request]
                        (controller/accommodation-declined request)))}}]
   ["/accommodationaccepted"
    {:get {:summary "Returns number of guests who want the accommodation at the wedding"
           :responses {200 {:body AccommodationAcceptedOutput}}
           :handler (wrap-with-context
                      (fn [request]
                        (controller/accommodation-accepted request)))}}]])
