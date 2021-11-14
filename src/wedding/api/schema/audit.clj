(ns wedding.api.schema.audit
  (:require [schema.core :as s]
            [wedding.api.schema.guest :refer [SubmitRSVP GuestOutput]])
  (:import (java.time LocalDateTime)))

(s/defschema AuditOutput
  {:id           s/Int
   :event        s/Str
   :time_created LocalDateTime
   :payload      (merge SubmitRSVP
                        {:rsvp_answered s/Bool}
                        {:record GuestOutput})})

(s/defschema AuditListOutput
  {:data  [AuditOutput]
   :count s/Int})
