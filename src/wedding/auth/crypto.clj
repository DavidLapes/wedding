(ns wedding.auth.crypto
  (:require [buddy.hashers :as hashers]
            [wedding.lib.env :refer [get-env]]))

(defn- encryption-salt []
  (get-env :wedding-jwt-salt))

(defn- encryption-config []
  {:alg  :pbkdf2+sha512
   :salt (encryption-salt)})

(defn encrypt
  "Encrypts given password."
  [password]
  (hashers/encrypt password (encryption-config)))

(defn check-password
  "Compares unencrypted password with encrypted one."
  [unencrypted-password encrypted-password]
  (hashers/check unencrypted-password encrypted-password))
