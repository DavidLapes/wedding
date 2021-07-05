(ns wedding.api.route.public.health-check
  (:require [wedding.api.controller.health-check :as controller]
            [wedding.lib.api.router :refer [wrap-with-context]]))

(def routes
  ["/health-check"
   {:get {:summary "Checks the health of the application"
          :responses {200 {:body {:message string?}}}
          :handler (wrap-with-context
                     (fn [_]
                       (controller/health-check)))}}])
