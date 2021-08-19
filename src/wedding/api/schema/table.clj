(ns wedding.api.schema.table
  (:require [schema.core :as s]
            [wedding.api.schema.address :refer [Address]]))

(s/defschema CreateTable
  {:description (s/maybe s/Str)
   :number      s/Str})

(s/defschema UpdateTable
  {:description (s/maybe s/Str)
   :number      s/Str})

(s/defschema TableOutput
  {:id            s/Int
   {:description  (s/maybe s/Str)
    :number       s/Str}})

(s/defschema TableListOutput
  {:data  [TableOutput]
   :count s/Int})
