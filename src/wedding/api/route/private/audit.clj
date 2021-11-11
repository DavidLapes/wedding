(ns wedding.api.route.private.audit
  (:require [schema.core :as s]
            [wedding.api.controller.audit :as controller]
            [wedding.lib.api.router :refer [wrap-with-context]]))

(def routes
  ["/audit"
   {:swagger {:tags ["Audit"]}}
   [""
    {:get  {:summary "Returns list of audit logs"
            :parameters {:query {:id s/Int}}
            :handler (wrap-with-context
                       (fn [request]
                         (controller/get request)))}}]])
