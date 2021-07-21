(ns wedding.api.controller.guest
  (:require [wedding.service.guest :as service]))

(defn rsvp
  "Creates RSVP record with a new guest."
  [{:keys [ctx/datasource body-params]}]
  (service/rsvp! datasource body-params))
