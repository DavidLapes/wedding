(ns wedding.api.schema.guest
  (:require [schema.core :as s]))

(s/defschema SubmitRSVP
  {:street                 s/Str
   :city                   s/Str
   :postal_code            s/Str
   :state                  s/Str
   :accommodation          s/Bool
   (s/optional-key :email) (s/maybe s/Str)
   :phone                  s/Str
   (s/optional-key :note)  (s/maybe s/Str)
   :language               (s/enum "czech" "english")})

(s/defschema CreateGuest
  {:first_name                    s/Str
   :last_name                     s/Str
   (s/optional-key :middle_name)  (s/maybe s/Str)
   :greeting_name                 s/Str
   (s/optional-key :table_id)     s/Int})

(s/defschema UpdateGuest
  {(s/optional-key :first_name)               s/Str
   (s/optional-key :last_name)                s/Str
   (s/optional-key :middle_name)              (s/maybe s/Str)
   (s/optional-key :greeting_name)            s/Str
   (s/optional-key :phone)                    (s/maybe s/Str)
   (s/optional-key :email)                    (s/maybe s/Str)
   (s/optional-key :street)                   (s/maybe s/Str)
   (s/optional-key :city)                     (s/maybe s/Str)
   (s/optional-key :postal_code)              (s/maybe s/Str)
   (s/optional-key :state)                    (s/maybe s/Str)
   (s/optional-key :accommodation)            (s/maybe s/Bool)
   (s/optional-key :note)                     (s/maybe s/Str)
   (s/optional-key :table_id)                 (s/maybe s/Int)
   (s/optional-key :language)                 (s/maybe s/Str)
   (s/optional-key :is_beer_drinker)          (s/maybe s/Bool)
   (s/optional-key :is_wine_drinker)          (s/maybe s/Bool)
   (s/optional-key :type)                     (s/enum "PRIMARY" "PLUS_ONE")
   (s/optional-key :escort_id)                (s/maybe s/Int)
   (s/optional-key :is_invitation_sent)       s/Bool
   (s/optional-key :invitation_delivery_type) (s/maybe (s/enum "MAIL" "IN_PERSON"))
   (s/optional-key :is_meat_eater)            s/Bool
   (s/optional-key :status)                   (s/enum "ACCEPTED" "REJECTED")})

(s/defschema GuestOutput
  {:id                            s/Int
   :first_name                    s/Str
   :middle_name                   (s/maybe s/Str)
   :last_name                     s/Str
   :greeting_name                 s/Str
   :phone                         (s/maybe s/Str)
   :email                         (s/maybe s/Str)
   :street                        (s/maybe s/Str)
   :city                          (s/maybe s/Str)
   :postal_code                   (s/maybe s/Str)
   :state                         (s/maybe s/Str)
   :accommodation                 (s/maybe s/Bool)
   :rsvp_answered                 s/Bool
   :note                          (s/maybe s/Str)
   :email_sent                    s/Bool
   :table_id                      (s/maybe s/Int)
   :language                      (s/maybe s/Str)
   :is_beer_drinker               (s/maybe s/Bool)
   :is_wine_drinker               (s/maybe s/Bool)
   :type                          (s/enum "PRIMARY" "PLUS_ONE")
   :type_name                     s/Str
   :escort_id                     (s/maybe s/Int)
   :escort_name                   (s/maybe s/Str)
   :is_invitation_sent            s/Bool
   :invitation_delivery_type      (s/maybe (s/enum "MAIL" "IN_PERSON"))
   :invitation_delivery_type_name (s/maybe s/Str)
   :is_meat_eater                 s/Bool})

(s/defschema GuestListOutput
  {:data  [GuestOutput]
   :count s/Int})

(s/defschema PublicGuestOutput
  {:id        s/Int
   :full_name s/Str})

(s/defschema PublicGuestListOutput
  {:data  [PublicGuestOutput]
   :count s/Int})
