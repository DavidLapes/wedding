(ns wedding.service.guest
  (:require [wedding.model.guest :as model]
            [clojure.java.jdbc :as jdbc]))

(defn create!
  "Creates new guest."
  [datasource data]
  (jdbc/with-db-transaction [connection {:datasource datasource}]
    (model/create! connection data)))

(defn update!
  "Updates an existing guest."
  [datasource id data]
  (jdbc/with-db-transaction [connection {:datasource datasource}]
    (model/update! connection id data)))

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
