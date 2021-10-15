(ns wedding.lib.email.template)

(defn rsvp-template [recipient {:keys [greeting_name accommodation
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
