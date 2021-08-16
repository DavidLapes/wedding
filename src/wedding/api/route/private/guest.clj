(ns wedding.api.route.private.guest
  (:require [wedding.api.controller.guest :as controller]
            [wedding.lib.api.router :refer [wrap-with-context]]))

(def routes
  ["/guests"
   {:post {:summary "Create new guest guest"
           ;;TODO: Validation
           :handler (wrap-with-context
                      (fn [request]
                        (controller/create request)))}}])
