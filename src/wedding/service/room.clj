(ns wedding.service.room
  (:require [clojure.java.jdbc :as jdbc]
            [wedding.model.room :as model]))

(defn create!
  "Creates new room."
  [datasource data]
  (jdbc/with-db-transaction [connection {:datasource datasource}]
    (model/create! connection data)))

(defn update!
  "Updates an existing room."
  [datasource id data]
  (jdbc/with-db-transaction [connection {:datasource datasource}]
    (model/update! connection id data)))

(defn get!
  "Returns list of rooms."
  [datasource]
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (model/get-all! connection)))

(defn get-all-available!
  "Returns list of available rooms."
  [datasource]
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (model/get-all-available! connection)))

(defn get-by-id!
  "Returns room by given id."
  [datasource id]
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (model/get-by-id! connection id)))

(defn delete!
  "Deletes room by id."
  [datasource id]
  (jdbc/with-db-transaction [connection {:datasource datasource}]
    (model/delete! connection id)))
