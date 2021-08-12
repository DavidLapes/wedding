(ns wedding.auth.core
  (:require [buddy.auth.backends :as backends]
            [buddy.sign.jwt :as jwt]
            [clojure.java.jdbc :as jdbc]
            [environ.core :refer [env]]
            [wedding.auth.crypto :as crypto]
            [wedding.model.user :as model]
            [wedding.service.user :as user-service])
  (:import (java.time LocalDateTime)))

(def jwt-secret ^{:doc "Secret for token signing"}
  (get env :jwt-secret "development-secret"))

(def auth-token-expiration-days 14)

(defn valid-token?
  "Checks if given parsed token is valid"
  [token]
  (let [expiration (:expiration token)
        date-time (LocalDateTime/parse expiration)]
    (.isAfter date-time (LocalDateTime/now))))

(defn authentication-fn
  [datasource]
   (fn [auth-data]
     (let [token-user (:user auth-data)
           email (:email token-user)
           model-user (user-service/get-by-email! datasource email)]
       (if (user-service/deleted? model-user)
         (throw (ex-info "User is deleted" {:cause :deleted}))
         (if (user-service/active? model-user)
           (if (valid-token? auth-data)
             model-user
             (throw (ex-info "Authentication token has expired" {:cause :expired-auth-token})))
           (throw (ex-info "User is inactive" {:cause :inactive})))))))

(def authentication-config ^{:doc "Configuration for Buddy-based authentication middleware wrapper"}
  (backends/jws {:realm "Wedding API"
                 :authfn authentication-fn
                 :token-name "Bearer"
                 :secret jwt-secret
                 :on-error (fn [_ ex]
                             (throw ex))}))

(defn get-login-detail-by-email!
  "Returns user by email."
  [datasource email]
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (model/get-login-detail-by-email! connection email)))

(defn generate-auth-token
  "Generates authentication token for given user."
  [user]
  (jwt/sign {:user       {:email (:email user)}
             :expiration (.plusDays (LocalDateTime/now) auth-token-expiration-days)}
            jwt-secret))

(defn sign-in
  "Signs in given user and returns generated token if authentication succeeds."
  [datasource email password]
  (let [user (get-login-detail-by-email! datasource email)
        encrypted-password (:password user)]
    (if (crypto/check-password password encrypted-password)
      (if (user-service/deleted? user)
        (throw (ex-info "User is deleted" {:cause :deleted}))
        (if (user-service/active? user)
          (generate-auth-token user)
          (throw (ex-info "User is inactive" {:cause :inactive}))))
      (throw (ex-info "Invalid password" {:cause :invalid-credentials})))))

(defn sign-up
  "Signs up new user and returns created user with new token if registration succeeds."
  [datasource user]
  (let [password (:password user)
        encrypted-password (crypto/encrypt password)
        user (merge user {:password encrypted-password})]
    (user-service/create! datasource user)))
