(ns microservice.component.proto.notification)

(defprotocol SNSNotificationAdapter
  (-notify [this data]))
