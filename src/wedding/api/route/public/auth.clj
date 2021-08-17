(ns wedding.api.route.public.auth
  (:require [wedding.api.controller.auth :as controller]
            [wedding.api.schema.auth :refer [SignIn AuthenticationToken]]
            [wedding.lib.api.router :refer [wrap-with-context]]))

(def routes
  ["/auth"
   ["/sign-in"
    {:post {:summary "Authenticate against Wedding API"
            :responses {200 {:body AuthenticationToken}}
            :parameters {:body SignIn}
            :handler (wrap-with-context
                       (fn [request]
                         (controller/sign-in request)))}}]])
