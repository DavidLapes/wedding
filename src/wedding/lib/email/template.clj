(ns wedding.lib.email.template)

(defn rsvp-template [recipient {:keys [greeting_name]}]
  {:recipient recipient
   :subject   "Vítejte na svatbě David & Terka"
   :text      (str
                "Hezký den Ti přejeme, " greeting_name "!\n\n
                 Tohle je potvrzovací email, že jsme úspěšně dostali veškeré informace, které dosud potřebujeme.
                 Plánujeme rozesílat pravidelné Newslettery (Novinky) v rámci e-mailu s novinkami ohledně plánování svatby a dalších informací, které by Tě mohli zajímat.
                 Pokud by Ti zasílání Newsletterů vadilo, můžeš níže v e-mailu proklikem na odkaz odebírání vypnout.\n\n
                 Přejeme krásný den a brzy od nás zase něco počteš (budeš-li chtít)
                 David Lapeš a Terka Borkovcová")})
;;TODO: Unsubscribe newsletters
