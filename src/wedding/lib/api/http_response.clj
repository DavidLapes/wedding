(ns wedding.lib.api.http-response)

(defn add-response-message [body message]
  (merge body {:message message}))

(defn response-message [message]
  {:message message})
