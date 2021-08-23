(ns wedding.api.schema.table
  (:require [schema.core :as s]))

(s/defschema CreateTable
  {:description (s/maybe s/Str)
   :number      s/Int
   :capacity    s/Int})

(s/defschema UpdateTable
  {:description (s/maybe s/Str)
   :number      s/Int
   :capacity    s/Int})

(s/defschema TableOutput
  {:id          s/Int
   :description (s/maybe s/Str)
   :number      s/Int
   :capacity    s/Int})

(s/defschema TableListOutput
  {:data  [TableOutput]
   :count s/Int})
