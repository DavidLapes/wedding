(ns wedding.model.audit
  (:require [wedding.lib.db.utils :as query]))

(def table-name :audit_log)

(defn get-all!
  "Returns all audit logs."
  ([connection]
   (get-all! connection {}))
  ([connection filters]
   (query/get-all! connection table-name (merge
                                           {:order_column    "time_created"
                                            :order_direction "desc"}
                                           filters))))
