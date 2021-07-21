(ns wedding.model.guest
  (:require [wedding.lib.db.utils :as query]))

(def table-name :guests)

(defn create!
  "Creates new guest."
  [connection guest]
  (query/insert! connection table-name guest))

(defn update!
  "Updates a guest."
  [connection id guest]
  (query/update-by-id! connection table-name id guest))

(defn get-by-id!
  "Returns guest by given id."
  [connection id]
  (query/get-by-id! connection table-name id))

(defn delete!
  "Deletes guest by id."
  [connection id]
  (query/delete! connection table-name {:id id}))
