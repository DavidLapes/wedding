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

(s/defschema BeerDrinkersOutput
  {:data  [GuestOutput]
   :count s/Int})

(s/defschema WineDrinkersOutput
  {:data [GuestOutput]
   :count s/Int})

(s/defschema GuestsWithRoomOutput
  {:data [GuestOutput]
   :count s/Int})

(s/defschema GuestsWithoutRoomOutput
  {:data [GuestOutput]
   :count s/Int})

(s/defschema BundledStatisticsOutput
  {:rsvp_answered_count          s/Int
   :rsvp_unanswered_count        s/Int
   :accommodation_accepted_count s/Int
   :accommodation_declined_count s/Int
   :beer_drinkers_count          s/Int
   :wine_drinkers_count          s/Int
   :guests_with_room_count       s/Int
   :guests_without_room_count    s/Int})
