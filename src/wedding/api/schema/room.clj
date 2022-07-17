(ns wedding.api.schema.room
  (:require [schema.core :as s]))

(s/defschema CreateRoom
  {:number    s/Str
   :bed_count s/Int})

(s/defschema RoomOutput
  {:id                                    s/Int
   :number                                s/Str
   :bed_count                             s/Int
   (s/optional-key :allocated_count)      s/Int
   (s/optional-key :allocated_guests)     [{:id        s/Int
                                            :full_name s/Str
                                            :room_id   s/Int}]
   (s/optional-key :available_beds_count) s/Int})

(s/defschema UpdateRoom
  {:number    s/Str
   :bed_count s/Int})

(s/defschema RoomListOutput
  {:data  [RoomOutput]
   :count s/Int})
