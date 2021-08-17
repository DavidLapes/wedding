(ns wedding.api.schema.message
  (:require [schema.core :as s]))

(s/defschema MessageResponse
  {:message s/Str})
