(ns wedding.lib.email.template)

(defn- rsvp-template-english [recipient {:keys [greeting_name accommodation
                                       first_name middle_name last_name
                                       email phone
                                       street city postal_code state
                                       note]}]
  {:recipient recipient
   :subject   "Welcome at Tereza's and David's wedding"
   :text      (str
                "<html>
                <body>
                Hello, " greeting_name "!
                <br/>
                <br/>
                This is a confirmation email, that we successfully got your information we need.
                We are gonna send regular Newsletters via email with news about our wedding and other information you could find interesting.
                If you don't want any emails sent, you can unsubscribe below.
                <br/>
                <br/>
                These are the information you filled up:
                <br/>
                <br/>
                <b>Full name: </b>" (if (some? middle_name) (str first_name " " middle_name " " last_name)
                                                                   (str first_name " " last_name)) "
                <br/>
                <b>Accommodation at the wedding: </b>" (if accommodation "Yes" "No") "
                <br/>
                <b>E-mail </b> " email "
                <br/>
                <b>Phone </b> " phone "
                <br/>
                <b>Street: </b> " street "
                <br/>
                <b>City: </b> " city "
                <br/>
                <b>Postal code: </b> " postal_code "
                <br/>
                <b>Country: </b> " state "
                <br/>"

                (if (clojure.string/blank? note)
                  ""
                  (str
                    "<br/>
                    <b>Note: </b> " note "
                    <br/>"))

                "<br/>
                <br/>
                Have a nice day and see you soon
                <br/>
                David Lapeš and Tereza Borkovcová
                </body
                </html>")})

(defn- rsvp-template-czech [recipient {:keys [greeting_name accommodation
                                       first_name middle_name last_name
                                       email phone
                                       street city postal_code state
                                       note]}]
  {:recipient recipient
   :subject   "Vítej na svatbě Davida a Terky"
   :text      (str
                "<html>
                <body>
                Hezký den Ti přejeme, " greeting_name "!
                <br/>
                <br/>
                Tohle je potvrzovací email, že jsme úspěšně dostali veškeré informace, které dosud potřebujeme.
                Plánujeme rozesílat pravidelné Newslettery (Novinky) v rámci e-mailu s novinkami ohledně plánování svatby a dalších informací, které by Tě mohli zajímat.
                Pokud by Ti zasílání Newsletterů vadilo, můžeš níže v e-mailu proklikem na odkaz odebírání vypnout.
                <br/>
                <br/>
                Údaje, které jsi zadal(a), jsou následující:
                <br/>
                <br/>
                <b>Jméno a příjmení: </b>" (if (some? middle_name) (str first_name " " middle_name " " last_name)
                                                                    (str first_name " " last_name)) "
                <br/>
                <b>Ubytování na svatbě: </b>" (if accommodation "Ano" "Ne") "
                <br/>
                <b>E-mail </b> " email "
                <br/>
                <b>Telefon </b> " phone "
                <br/>
                <b>Ulice: </b> " street "
                <br/>
                <b>Město: </b> " city "
                <br/>
                <b>PSČ: </b> " postal_code "
                <br/>
                <b>Stát: </b> " state "
                <br/>"

                (if (clojure.string/blank? note)
                  ""
                  (str
                    "<br/>
                    <b>Poznámka: </b> " note "
                    <br/>"))

                "<br/>
                <br/>
                Přejeme krásný den a brzy od nás zase něco počteš (budeš-li chtít)
                <br/>
                David Lapeš a Terka Borkovcová
                </body
                </html>")})
;;TODO: Unsubscribe newsletters

(defn rsvp-template [recipient data language]
  (if (some? language)
    (if (= language "czech")
      (rsvp-template-czech recipient data)
      (rsvp-template-english recipient data))
    (throw (ex-info "Missing language for RSVP template" {:cause :rsvp-language-missing}))))
