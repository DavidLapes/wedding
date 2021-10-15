(ns wedding.api.schema.guest
  (:require [schema.core :as s]))

(s/defschema SubmitRSVP
  {:street        s/Str
   :city          s/Str
   :postal_code   s/Str
   :state         s/Str
   :accommodation s/Bool
   :email         (s/maybe s/Str)
   :phone         s/Str
   :note          (s/maybe s/Str)})

(s/defschema CreateGuest
  {:first_name                    s/Str
   :last_name                     s/Str
   (s/optional-key :middle_name)  (s/maybe s/Str)
   :greeting_name                 s/Str
   (s/optional-key :table_id)     s/Int})

(s/defschema UpdateGuest
  {(s/optional-key :first_name)    s/Str
   (s/optional-key :last_name)     s/Str
   (s/optional-key :table_id)      s/Int
   (s/optional-key :greeting_name) s/Str})

(s/defschema GuestOutput
  {:id            s/Int
   :first_name    s/Str
   :middle_name   (s/maybe s/Str)
   :last_name     s/Str
   :greeting_name s/Str
   :phone         (s/maybe s/Str)
   :email         (s/maybe s/Str)
   :street        (s/maybe s/Str)
   :city          (s/maybe s/Str)
   :postal_code   (s/maybe s/Str)
   :state         (s/maybe s/Str)
   :accommodation (s/maybe s/Bool)
   :rsvp_answered s/Bool
   :note          (s/maybe s/Str)
   :email_sent    s/Bool
   :table_id      (s/maybe s/Int)})

(s/defschema GuestListOutput
  {:data  [GuestOutput]
   :count s/Int})

(s/defschema PublicGuestOutput
  {:id        s/Int
   :full_name s/Str})

(s/defschema PublicGuestListOutput
  {:data  [PublicGuestOutput]
   :count s/Int})
