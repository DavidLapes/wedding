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
            :parameters {:query {(s/optional-key :id) s/Int}}
            :responses {200 {:body AuditListOutput}}
            :handler (wrap-with-context
                       (fn [request]
                         (controller/get request)))}}]])
