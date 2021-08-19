(ns wedding.model.table
  (:require [wedding.lib.db.utils :as query]))

(def table-name :tables)

(defn create!
  "Creates new table."
  [connection table]
  (query/insert! connection table-name table))

(defn update!
  "Updates a table."
  [connection id table]
  (query/update-by-id! connection table-name id table))

(defn get-by-id!
  "Returns table by given id."
  [connection id]
  (query/get-by-id! connection table-name id))

(defn get-all!
  "Returns all tables."
  ([connection]
   (get-all! connection {}))
  ([connection filters]
   (query/get-all! connection table-name filters)))

(defn delete!
  "Deletes table by id."
  [connection id]
  (query/delete! connection table-name {:id id}))
