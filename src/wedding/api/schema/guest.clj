(ns wedding.api.schema.guest
  (:require [schema.core :as s]
            [wedding.api.schema.address :refer [Address]]))

(s/defschema SubmitRSVP
  {:id            s/Int
   :address       Address
   :accommodation s/Bool
   :email         s/Str
   :phone         s/Str})

(s/defschema CreateGuest
  {:first_name  s/Str
   :last_name   s/Str})

(s/defschema UpdateGuest
  {:first_name  (s/maybe s/Str)
   :last_name   (s/maybe s/Str)
   :table_id    (s/maybe s/Int)})

(s/defschema GuestOutput
  {:id                  s/Int
   :first_name          s/Str
   :middle_name         (s/maybe s/Str)
   :last_name           s/Str
   :phone               (s/maybe s/Str)
   :email               (s/maybe s/Str)
   :state               (s/maybe s/Str)
   :city                (s/maybe s/Str)
   :street              (s/maybe s/Str)
   :orientation_number  (s/maybe s/Str)
   :descriptive_number  (s/maybe s/Str)
   :postal_code         (s/maybe s/Str)
   :rsvp_answered       s/Bool
   :email_sent          s/Bool
   :table_id            (s/maybe s/Int)})

(s/defschema GuestListOutput
  {:data  [GuestOutput]
   :count s/Int})
