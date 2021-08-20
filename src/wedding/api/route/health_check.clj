(ns wedding.api.route.health-check
  (:require [wedding.api.controller.health-check :as controller]
            [wedding.api.schema.message :refer [MessageResponse]]
            [wedding.lib.api.router :refer [wrap-with-context]]))

(def routes
  ["/health-check"
   {:swagger {:tags ["Health"]}
    :get {:summary "Checks the health of the application"
          :responses {200 {:body MessageResponse}}
          :handler (wrap-with-context
                     (fn [_]
                       (controller/health-check)))}}])
