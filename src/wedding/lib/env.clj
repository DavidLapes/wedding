(ns wedding.lib.env
  (:require [environ.core :refer [env]]))

(def ^:private env-config
  {:aws-access-key                  {:required? true}
   :aws-secret-key                  {:required? true}
   :aws-region                      {:required? true}
   :wedding-db-host                 {:required? true}
   :wedding-db-port                 {:required? true}
   :wedding-db-name                 {:required? true}
   :wedding-db-user                 {:required? true}
   :wedding-db-password             {:required? true}
   :wedding-web-server-port         {:required? false
                                     :default   5000}
   :wedding-web-server-thread-count {:required? false
                                     :default   8}
   :wedding-jwt-salt                {:required? false
                                     :default   ".X5/AuAh4Wc.arYkJ!5StEI=m/17zh-Rvvl5ZtkS4Vb+o(u/I@O76RnAN99i*CX(34(KMhRrwB3K/mNe=1CM/TGLxcqut_1s_zXBFBrFN0vk8ToAOg3qV2U(4IN)vt7KxbSM/7!r!FxH!l?g.Kh3:q=hVDvOB1!TGSirDJNnwSs(#f-SeetQJLm?Czl0/MhW4+TT00UW*2H+OT67mN9WcMWNTJ02+MUwio#n_W(dN(gde?FT.bsACPGL4SoBqmhf"}
   :wedding-jwt-secret              {:required? false
                                     :default   "development-secret"}})

(defn- valid-config? [config]
  (boolean? (:required? config)))

(defn- get-env-value [key config]
  (let [value (get env key)
        default-value (:default config)
        result (or value default-value)
        required? (:required? config)]
    (if required?
      (if (some? result)
        result
        (throw (ex-info (str "Missing required ENVIRONMENT VARIABLE value for " key "") {:cause :environment-variable-not-set})))
      result)))

(def ^:private envs
  (reduce-kv
    (fn [map key config]
      (if (valid-config? config)
        (let [value (get-env-value key config)]
          (assoc map key value))
        (throw (ex-info (str "Invalid ENVIRONMENT VARIABLE configuration " key " of config " config) {:cause :environment-variable-misconfiguration}))))
    {}
    env-config))

(defn get-env [key]
  (key envs))
