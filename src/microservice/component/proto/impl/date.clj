(ns microservice.component.proto.impl.date
  (:require [clojure.java.jdbc :as jdbc]
            #_[ring.swagger.json-schema :as json-schema])
  (:import (java.time LocalDateTime)
           (java.sql Timestamp)))

(extend-protocol jdbc/IResultSetReadColumn
  Timestamp
  (result-set-read-column [v _2 _3]
    (.toLocalDateTime v)))

(extend-protocol jdbc/ISQLValue
  LocalDateTime
  (sql-value [v]
    (Timestamp/valueOf v)))

#_(defmethod json-schema/convert-class LocalDateTime [_ _]
  {:type "string" :format "date"})
