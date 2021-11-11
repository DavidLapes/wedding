(ns wedding.service.audit
  (:require [clojure.java.jdbc :as jdbc]
            [wedding.model.audit :as model]))

(defn get!
  "Returns list of audit logs."
  [datasource filters]
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (model/get-all! connection filters)))
