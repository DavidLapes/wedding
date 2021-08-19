(ns wedding.service.table
  (:require [clojure.java.jdbc :as jdbc]
            [wedding.model.table :as model]))

(defn create!
  "Creates new table."
  [datasource data]
  ;;TODO: Validation
  (jdbc/with-db-transaction [connection {:datasource datasource}]
    (model/create! connection data)))

(defn update!
  "Updates an existing table."
  [datasource id data]
  (jdbc/with-db-transaction [connection {:datasource datasource}]
    (model/update! connection id data)))

(defn get!
  "Returns list of tables."
  [datasource]
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (model/get-all! connection)))

(defn get-by-id!
  "Returns table by given id."
  [datasource id]
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (model/get-by-id! connection id)))

(defn delete!
  "Deletes table by id."
  [datasource id]
  (jdbc/with-db-transaction [connection {:datasource datasource}]
    (model/delete! connection id)))
