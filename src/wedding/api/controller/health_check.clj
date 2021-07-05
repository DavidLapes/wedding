(ns wedding.api.controller.health-check
  (:require [wedding.lib.api.http-response :refer [response-message]]
            [ring.util.http-response :refer [ok]]))

(defn health-check []
  (ok (response-message "The API is healthy")))
