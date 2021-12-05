(ns microservice.component.middleware.exception
  (:require [reitit.ring.middleware.exception :as exception]
            [ring.util.http-status :refer [internal-server-error unauthorized bad-request]]
            [taoensso.timbre :as timbre]
            [wedding.lib.api.http-response :refer [response-message]])
  (:import (java.sql SQLException)
           (org.postgresql.util PSQLException)))

(def ^:private exception-codes
  {:expired-auth-token               {:code    unauthorized
                                      :message "Přihlášení vypršelo, prosím, přihlašte se znovu"}
   :invalid-credentials              {:code    unauthorized
                                      :message "Neplatné přihlašovací údaje"}
   :not-authenticated                {:code    unauthorized
                                      :message "Nejsi přihlášen(a), prosím, přihlas se"}
   :rsvp-for-guest-already-answered  {:code    bad-request
                                      :message "Tvá účast může být potvrzena pouze jednou"}
   :modify-guest-failed              {:code    internal-server-error
                                      :message "Úprava hosta se nezdařila"}
   :delete-guest-failed              {:code    internal-server-error
                                      :message "Smazání hosta se nezdařilo"}})

(defn- get-exception-cause [exception]
  (let [class (type exception)]
    (condp isa? class
      PSQLException           (-> (.getServerErrorMessage exception) (.toString))
      (-> (ex-data exception) :cause))))

(defn- exception-handler [default-message exception request]
  (let [cause (get-exception-cause exception)]
    (let [response (if-let [known-exception (cause exception-codes)]
                     {:status (:code known-exception)
                      :body   (-> known-exception :message (response-message))}
                     {:status internal-server-error
                      :body   (str "Internal Server Error: " "{" default-message "} - " cause)})]
      (println response)
      response)))

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
