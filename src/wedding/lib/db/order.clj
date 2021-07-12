(ns wedding.lib.db.order
  (:require [clojure.string :refer [lower-case]]
            [honeysql.helpers :as honey]))

(defn- extract-order-column
  "Returns keywordized order column filter from filters map."
  [filters]
  (when-let [column (:order_column filters)]
    (-> (lower-case column)
        keyword)))

(defn- extract-order-direction
  "Returns keywordized order direction filter from filters map."
  [filters]
  (when-let [direction (:order_direction filters)]
    (condp = (lower-case direction)
      "asc" :asc
      "desc" :desc
      :else (throw (ex-info "Invalid order direction" {:type  :validation
                                                       :cause :invalid-order-dir})))))

(defn apply-order-filter
  "Adds ORDER BY clause to HoneySQL query. Accepted keys are:

  :order_column  \"name\"       -   required
  :order_direction \"asc\"      -   optional, can be nil."
  [query filters]
  (let [order-column (extract-order-column filters)
        order-direction (extract-order-direction filters)]
    (if order-column
      (honey/order-by query [order-column order-direction])
      query)))
