(ns wedding.api.route.public.auth
  (:require [wedding.api.controller.auth :as controller]
            [wedding.lib.api.router :refer [wrap-with-context]]))

(def ^:private sign-in
  ["/sign-in"
   {:post {:summary "Authenticate against Wedding API"
           :responses {200 {:body {:token string?}}}
           :handler (wrap-with-context
                      (fn [request]
                        (controller/sign-in request)))}}])

(def routes
  ["/auth"
   sign-in])
