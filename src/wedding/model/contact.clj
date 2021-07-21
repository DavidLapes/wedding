(ns wedding.model.contact
  (:require [wedding.lib.db.utils :as query]))

(def table-name :contacts)

(defn create!
  "Creates new contact."
  [connection contact]
  (query/insert! connection table-name contact))

(defn update!
  "Updates a contact."
  [connection id contact]
  (query/update-by-id! connection table-name id contact))

(defn get-by-id!
  "Returns contact by given id."
  [connection id]
  (query/get-by-id! connection table-name id))

(defn delete!
  "Deletes contact by id."
  [connection id]
  (query/delete! connection table-name {:id id}))
