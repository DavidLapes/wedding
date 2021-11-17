(ns wedding.api.route.private.audit
  (:require [schema.core :as s]
            [wedding.api.controller.audit :as controller]
            [wedding.api.schema.audit :refer [AuditListOutput]]
            [wedding.lib.api.router :refer [wrap-with-context]]))

(def routes
  ["/audit"
   {:swagger {:tags ["Audit"]}}
   [""
    {:get  {:summary "Returns list of audit logs"
            :parameters {:query {(s/optional-key :id)            s/Int
                                 (s/optional-key :guest_id)      s/Int
                                 (s/optional-key :event)         s/Str
                                 (s/optional-key :first_name)    s/Str
                                 (s/optional-key :last_name)     s/Str
                                 (s/optional-key :city)          s/Str
                                 (s/optional-key :street)        s/Str
                                 (s/optional-key :state)         s/Str
                                 (s/optional-key :postal_code)   s/Str}}
            :responses {200 {:body AuditListOutput}}
            :handler (wrap-with-context
                       (fn [request]
                         (controller/get request)))}}]])
