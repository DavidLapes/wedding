(ns wedding.auth.crypto
  (:require [buddy.hashers :as hashers]
            [environ.core :refer [env]]))

(def ^:private encryption-salt
  (get env :wedding-jwt-salt ".X5/AuAh4Wc.arYkJ!5StEI=m/17zh-Rvvl5ZtkS4Vb+o(u/I@O76RnAN99i*CX(34(KMhRrwB3K/mNe=1CM/TGLxcqut_1s_zXBFBrFN0vk8ToAOg3qV2U(4IN)vt7KxbSM/7!r!FxH!l?g.Kh3:q=hVDvOB1!TGSirDJNnwSs(#f-SeetQJLm?Czl0/MhW4+TT00UW*2H+OT67mN9WcMWNTJ02+MUwio#n_W(dN(gde?FT.bsACPGL4SoBqmhf"))

(def ^:private encryption-config
  {:alg  :pbkdf2+sha512
   :salt encryption-salt})

(defn encrypt
  "Encrypts given password."
  [password]
  (hashers/encrypt password encryption-config))

(defn check-password
  "Compares unencrypted password with encrypted one."
  [unencrypted-password encrypted-password]
  (hashers/check unencrypted-password encrypted-password))
