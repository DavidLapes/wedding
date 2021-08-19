(ns wedding.api.schema.address
  (:require [schema.core :as s]))

(s/defschema Address
  {:postal-code         s/Str
   :city                s/Str
   :street              s/Str
   :orientation-number  (s/maybe s/Str)
   :descriptive-number  (s/maybe s/Str)})
