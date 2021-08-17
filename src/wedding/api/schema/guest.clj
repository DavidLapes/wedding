(ns wedding.api.schema.guest
  (:require [schema.core :as s]))

(s/defschema CreateGuest
  {:first-name  s/Str
   :last-name   s/Str})
