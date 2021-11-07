(ns wedding.service.guest
  (:require [clojure.java.jdbc :as jdbc]
            [microservice.component.proto.notification :refer [-notify]]
            [schema.core :as s]
            [taoensso.timbre :as timbre]
            [wedding.api.schema.guest :refer [SubmitRSVP]]
            [wedding.model.guest :as model]
            [wedding.lib.email.template :as template]))

(defn create!
  "Creates new guest."
  [datasource data]
  (jdbc/with-db-transaction [connection {:datasource datasource}]
    (model/create! connection data)))

(defn update!
  "Updates an existing guest."
  [datasource id data]
  (jdbc/with-db-transaction [connection {:datasource datasource}]
    (model/update! connection id data)))

(defn get!
  "Returns list of guests."
  [datasource]
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (model/get-all! connection)))

(defn get-by-id!
  "Returns guest by given id."
  [datasource id]
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (model/get-by-id! connection id)))

(defn delete!
  "Deletes guest by id."
  [datasource id]
  (jdbc/with-db-transaction [connection {:datasource datasource}]
    (model/delete! connection id)))

(defn rsvp!
  "Creates RSVP record with a new guest."
  [datasource email-notification-adapter id data]
  (s/validate SubmitRSVP data)
  (jdbc/with-db-transaction [connection {:datasource datasource}]
    (let [email (:email data)
          guest-record (model/get-by-id! connection id)
          notify-options (template/rsvp-template email
                                                 (merge guest-record
                                                        {:accommodation (:accommodation data)
                                                         :email         (:email data)
                                                         :phone         (:phone data)
                                                         :city          (:city data)
                                                         :street        (:street data)
                                                         :postal_code   (:postal_code data)
                                                         :state         (:state data)
                                                         :note          (:note data)})
                                                 (:language data))
          data (merge data {:rsvp_answered true})]
      (if (:rsvp_answered guest-record)
        (throw (ex-info (str "RSVP record for given guest ID - " id " - already exists") {:cause :rsvp-for-guest-already-answered}))
        (model/update! connection id data))
      (try
        (when (some? email)
          (timbre/info "Preparing notification for guest ID: " id)
          (-notify email-notification-adapter notify-options)
          (timbre/info (str "Disabling additional RSVP forms for guest ID: " id))
          (model/update! connection id {:email_sent true}))
        (timbre/info (str "Created RSVP record for guest ID: " id))
        :success
        (catch Exception e
          (timbre/error "Creation of RSVP has FAILED")
          (jdbc/db-set-rollback-only! connection)
          (timbre/error e)
          :failed)))))

(defn get-rsvp-guests!
  "Returns list of guests who have not responded to RSVP form yet."
  [datasource]
  (jdbc/with-db-connection [connection {:datasource datasource}]
    (model/get-rsvp-guests! connection)))

;;-----------------------------------------------------------------------------------------

(comment

  (require '[dev-user :as dev])

  (def datasource (-> @dev/system :wedding.component/datasource :datasource))
  (def email-notification-adapter (-> @dev/system :wedding.component/email-notification-adapter ))

  (create! datasource {:first_name "David"
                       :last_name "Lapes"
                       :greeting_name "Davide"
                       :rsvp_answered true})

  (create! datasource {:first_name "David"
                       :last_name "Lapes"
                       :greeting_name "Davide"})

  (rsvp-guests! datasource)

  (rsvp! datasource
         email-notification-adapter
         9
         {:state              "Pragueland"
          :city               "Prague"
          :street             "Pragueish"
          :orientation_number "878"
          :descriptive_number "12"
          :postal_code        "12345"
          :accommodation      true
          :email              "dave.lapes@gmail.com"
          :phone              "+420123456789"})

  ;;David
  (let [guest-id (-> (create! datasource {:first_name "David"
                                          :last_name "Lapes"
                                          :greeting_name "Davide"})
                     :id)]
    (rsvp! datasource
           email-notification-adapter
           guest-id
           {:state              "Pragueland"
            :city               "Prague"
            :street             "Pragueish"
            :orientation_number "878"
            :descriptive_number "12"
            :postal_code        "12345"
            :accommodation      true
            :email              "dave.lapes@gmail.com"
            :phone              "+420123456789"}))

  ;;Terka
  (let [guest-id (-> (create! datasource {:first_name "Tereza"
                                          :last_name "Borkovcova"
                                          :greeting_name "Terko"})
                     :id)]
    (rsvp! datasource
           email-notification-adapter
           guest-id
           {:state              "Pragueland"
            :city               "Prague"
            :street             "Pragueish"
            :orientation_number "878"
            :descriptive_number "12"
            :postal_code        "12345"
            :accommodation      true
            :email              "terkaborkovcova@gmail.com"
            :phone              "+420123456789"}))

  (def result
    (-notify email-notification-adapter {:recipient  "dave.lapes@gmail.com"
                                         :subject    "Welcome"
                                         :text       "Welcome, here!"})))
