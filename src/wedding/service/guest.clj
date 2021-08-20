(ns wedding.service.guest
  (:require [clojure.java.jdbc :as jdbc]
            [wedding.model.guest :as model]))

(defn create!
  "Creates new guest."
  [datasource data]
  ;;TODO: Validation
  (jdbc/with-db-transaction [connection {:datasource datasource}]
    (model/create! connection data)))

(defn update!
  "Updates an existing guest."
  [datasource id data]
  (jdbc/with-db-transaction [connection {:datasource datasource}]
    (model/update! connection id data)))

(defn get!
  "Returns list of guests."
  [datasource]
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (model/get-all! connection)))

(defn get-by-id!
  "Returns guest by given id."
  [datasource id]
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (model/get-by-id! connection id)))

(defn delete!
  "Deletes guest by id."
  [datasource id]
  (jdbc/with-db-transaction [connection {:datasource datasource}]
    (model/delete! connection id)))

(defn rsvp!
  "Creates RSVP record with a new guest."
  [datasource data]
  (jdbc/with-db-transaction [connection {:datasource datasource}]
    (model/create! connection data)))
