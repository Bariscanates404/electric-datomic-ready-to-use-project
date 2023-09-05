(ns app.poms-01-login-20230828
  (:require contrib.str
            [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]
    ;datomic require'ı asagıdaki
            #?(:clj [datomic.client.api :as d])
            [hyperfiddle.electric-ui4 :as ui]
            [hyperfiddle.history :as router]
            ))

;; bu kısımda zorunlu
(e/def conn)
(e/def db)
;; bu kısımda zorunlu


#?(:clj (defn login-check [user-id]
          (if (clojure.string/blank? (str user-id))
            false
            true)
          )
   )
#?(:clj (defn login-test [username password db] (ffirst (d/q
                                                          '[:find ?result
                                                            :in $ ?name ?password
                                                            :where
                                                            [?e :user/name ?name]
                                                            [?e :user/password ?password]
                                                            [?e :user/id ?id]
                                                            [(app.poms-01-login-20230828/login-check ?id) ?result]
                                                            ]
                                                          db username password)
                                                        )
          )
   )


(e/defn login-page []
        (e/server
          (binding [conn @(requiring-resolve 'user/datomic-conn)]
            (binding [db (d/db conn)]
              ;bind etmek zorundayız dbyi calıstırmak için.
              (e/client
                (let [!state (atom {
                                    :in         ""
                                    :in2        ""
                                    :button1    ""
                                    :button2    ""
                                    :login-outh ""
                                    :login-info "Welcome! Colabio makes life easier..."
                                    })
                      in (get (e/watch !state) :in)
                      in2 (get (e/watch !state) :in2)
                      ]
                  (dom/element "style" (dom/text "
                  ul{list-style-type: none; margin: 0; padding: 0; background-color: black; overflow: auto; }
                  li {float: left;}
                  li a {color: white; padding: 15px 25px; display: inline-block; text-align: center; text-decoration: none;}
                  .home {background-color: darkred;}
                  li a:hover {
                  background-color: #405d27;
                  }"))


                  (dom/ul (dom/props {:class "ul"})
                          (dom/li
                            (dom/a (dom/props {:class "home" :href "http://localhost:8080/"})
                                   (dom/text "Home")))
                          (dom/li
                            (dom/a (dom/props {:href "http://localhost:8080/(app.poms-02-createprojectpage-20230901!create-project)"})
                                   (dom/text "Create Project")))
                          (dom/li
                            (dom/a (dom/props {:href "#contact"})
                                   (dom/text "contact")))
                          (dom/li
                            (dom/a (dom/props {:href "#about"})
                                   (dom/text "about")))
                          )

                  (dom/div
                    (dom/form
                      (dom/props {:il "FORM"
                                  })
                      (dom/fieldset
                        (dom/props {:name "FORM"
                                    })
                        (dom/legend
                          (dom/text "POMS LOGIN")
                          (dom/props {:style {:text-align "center"}}))

                        (dom/p (dom/text "USERNAME:")
                               (ui/input in
                                         (e/fn [v] (swap! !state assoc :in v))
                                         (dom/on "keyup" (e/fn [keyup]
                                                               (when-some [givenValue (contrib.str/empty->nil (->
                                                                                                                keyup
                                                                                                                .-target
                                                                                                                .-value))]
                                                                 (swap! !state assoc :button1 givenValue)
                                                                 )))
                                         (dom/props {:style {:name "USERNAME"}})
                                         ))
                        (dom/p (dom/text "PASSWORD:")
                               (ui/input in2
                                         (e/fn [v] (swap! !state assoc :in2 v))
                                         (dom/on "keyup" (e/fn [keyup]
                                                               (when-some [givenValue (contrib.str/empty->nil (->
                                                                                                                keyup
                                                                                                                .-target
                                                                                                                .-value))]
                                                                 (swap! !state assoc :button2 givenValue)
                                                                 )))
                                         (dom/props {:style {:name "PASSWORD"}})
                                         ))
                        (ui/button
                          (e/fn []
                                  (swap! !state assoc
                                         :login-outh (e/server (login-test (e/client (get (e/watch !state) :in)) (e/client (get (e/watch !state) :in2)) db))
                                         )


                                )

                          (dom/text "Login")
                          (dom/props {:class "button1"
                                      :name  "login-button"
                                      :style {:text-align "center"}
                                      }))
                        (dom/p (dom/text (get (e/watch !state) :login-info)
                                         (dom/props {:class "login-info"
                                                     :name  "login-text"
                                                     })))
                        (swap! !state assoc :login-info (if (= (get (e/watch !state) :login-outh) true)
                                                          "login succesfully completed!"
                                                          "Wrong username or password!")
                               )

                        )
                      )
                    )
                  )
                )
              )
            )
          )
        )

