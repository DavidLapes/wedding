(ns wedding.api.controller.auth
  (:require [ring.util.http-response :refer [ok]]
            [wedding.auth.core :as auth]))

(defn sign-in
  "Signs in given user and returns generated token if authentication succeeds."
  [{:keys [ctx body-params]}]
  (let [token (auth/sign-in (:datasource ctx) (:email body-params) (:password body-params))]
    (ok {:token token})))
