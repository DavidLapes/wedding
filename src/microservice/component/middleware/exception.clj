(ns microservice.component.middleware.exception
  (:require [reitit.ring.middleware.exception :as exception]
            [taoensso.timbre :as timbre]
            [wedding.lib.api.http-response :refer [response-message]])
  (:import (java.sql SQLException)
           (org.postgresql.util PSQLException)))

(defn- get-exception-cause [exception]
  (let [class (type exception)]
    (condp isa? class
      PSQLException           (-> (.getServerErrorMessage exception) (.toString))
      (-> (ex-data exception) :cause))))

(defn- get-exception-message [exception default-message]
  (let [cause (get-exception-cause exception)]
    (case cause
      :invalid-credentials              "Invalid credentials provided"
      :rsvp-for-guest-already-answered  "Tvá účast může být potvrzena pouze jednou"
      :not-authenticated                "Missing credentials header"
      :modify-guest-failed              "Modification of guest has failed"
      :delete-guest-failed              "Deletion of guest has failed"
      (str "Internal Server Error: " "{" default-message "} - " cause))))

(defn- exception-handler [message exception request]
  ;;FIXME
  {:status 500
   :body (response-message (get-exception-message exception message))})

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
       ::exception/wrap    (fn [handler exception request]
                             (timbre/error exception)
                             (println "ERROR" (pr-str (:uri request)))
                             (handler exception request))})))
