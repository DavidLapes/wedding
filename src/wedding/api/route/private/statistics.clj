(ns wedding.api.route.private.statistics
  (:require [wedding.api.controller.statistics :as controller]
            [wedding.api.schema.statistics :refer [BundledStatisticsOutput RSVPAnsweredOutput RSVPUnansweredOutput
                                                   AccommodationAcceptedOutput AccommodationDeclinedOutput
                                                   BeerDrinkersOutput WineDrinkersOutput
                                                   GuestsWithRoomOutput GuestsWithoutRoomOutput]]
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
                        (controller/accommodation-accepted request)))}}]
   ["/beerdrinkers"
    {:get {:summary "Returns number of guests who will drink beer at the wedding"
           :responses {200 {:body BeerDrinkersOutput}}
           :handler (wrap-with-context
                      (fn [request]
                        (controller/beer-drinkers request)))}}]
   ["/winedrinkers"
    {:get {:summary "Returns number of guests who will drink wine at the wedding"
           :responses {200 {:body WineDrinkersOutput}}
           :handler (wrap-with-context
                      (fn [request]
                        (controller/wine-drinkers request)))}}]
   ["/guestswithroom"
    {:get {:summary "Returns number of guests who have room assigned"
           :responses {200 {:body GuestsWithRoomOutput}}
           :handler (wrap-with-context
                      (fn [request]
                        (controller/guests-with-room request)))}}]
   ["/guestswithoutroom"
    {:get {:summary "Returns number of guests who are waiting for a room"
           :responses {200 {:body GuestsWithoutRoomOutput}}
           :handler (wrap-with-context
                      (fn [request]
                        (controller/guests-without-room request)))}}]])
