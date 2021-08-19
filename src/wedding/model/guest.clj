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

(defn rsvp!
  "Creates RSVP record for a guest."
  [connection id rsvp]
  ;TODO - Also write test for this for testability issues
  ;TODO - Need to add a field to make RSVP form being doable only o
  )

(defn get-by-id!
  "Returns guest by given id."
  [connection id]
  (query/get-by-id! connection table-name id))

(defn get-all!
  "Returns all guests."
  ([connection]
   (get-all! connection {}))
  ([connection filters]
   (query/get-all! connection table-name filters)))

(defn delete!
  "Deletes guest by id."
  [connection id]
  (query/delete! connection table-name {:id id}))
