(ns wedding.api.schema.statistics
  (:require [schema.core :as s]
            [wedding.api.schema.guest :refer [GuestOutput]]))

(s/defschema RSVPAnsweredOutput
  {:data  [GuestOutput]
   :count s/Int})

(s/defschema RSVPUnansweredOutput
  {:data  [GuestOutput]
   :count s/Int})

(s/defschema AccommodationAcceptedOutput
  {:data  [GuestOutput]
   :count s/Int})

(s/defschema AccommodationDeclinedOutput
  {:data  [GuestOutput]
   :count s/Int})

(s/defschema BundledStatisticsOutput
  {:rsvp_answered_count          s/Int
   :rsvp_unanswered_count        s/Int
   :accommodation_accepted_count s/Int
   :accommodation_declined_count s/Int})
