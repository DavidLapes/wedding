(ns wedding.auth.middleware
  (:require [buddy.auth.middleware :as auth]
            [wedding.auth.core :refer [authentication-config]]))

(defn wrap-with-jwt-middleware
  "Returns Ring middleware for JWT-based authentication."
  [datasource handler]
  (auth/wrap-authentication handler (authentication-config datasource)))
