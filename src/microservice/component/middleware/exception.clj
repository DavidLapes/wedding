(ns microservice.component.middleware.exception
  (:require [reitit.ring.middleware.exception :as exception])
  (:import (java.sql SQLException)))

(defn- get-exception-message [exception default-message]
  (let [cause (-> (ex-data exception) :cause)]
    (case cause
      :invalid-credentials  "Invalid credentials provided"
      :else                 (str "Internal Server Error: " default-message))))

(defn- exception-handler [message exception request]
  {:status 500
   :body {:message (get-exception-message exception message)}})

(def exception-middleware
  (exception/create-exception-middleware
    (merge
      exception/default-handlers
      {;; ex-data with :type ::error
       ::error             (partial exception-handler "error")

       ;; ex-data with ::exception or ::failure
       ::exception         (partial exception-handler "exception")

       ;; SQLException and all it's child classes
       SQLException        (partial exception-handler "sql-exception")

       ;; override the default handler
       ::exception/default (partial exception-handler "default")

       ;; print stack-traces for all exceptions
       ::exception/wrap    (fn [handler e request]
                             (println "ERROR" (pr-str (:uri request)))
                             (handler e request))})))
