(ns app.poms-00-signup-20230831
  (:require contrib.str
            [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]
    ;datomic require'ı asagıdaki
            #?(:clj [datomic.client.api :as d])
            [hyperfiddle.electric-ui4 :as ui]
    ;router icin hyperfiddle.history'i kullanıyoruz
            [hyperfiddle.history :as router]

            ))

;; bu kısımda zorunlu
(e/def conn)
(e/def db)
;; bu kısımda zorunlu


(e/defn signup-page []
        (e/server
          (binding [conn @(requiring-resolve 'user/datomic-conn)]
            (binding [db (d/db conn)]
              ;bind etmek zorundayız dbyi calıstırmak için.
              (e/client
                (let [!state (atom {
                                    :username       ""
                                    :password       ""
                                    :formal-name    ""
                                    :formal-surname ""
                                    :brand-name     ""
                                    :email          ""
                                    :phonenumber    ""
                                    :category       ""
                                    })
                      username (get (e/watch !state) :username)
                      password (get (e/watch !state) :password)
                      formal-name (get (e/watch !state) :formal-name)
                      formal-surname (get (e/watch !state) :formal-surname)
                      brand-name (get (e/watch !state) :brand-name)
                      email (get (e/watch !state) :email)
                      phonenumber (get (e/watch !state) :phonenumber)
                      ]
                  (dom/element "style" (dom/text "
                  ul{list-style-type: none; margin: 0; padding: 0; background-color: black; overflow: auto; }
                  li {float: left;}
                  li a {color: white; padding: 15px 25px; display: inline-block; text-align: center; text-decoration: none;}
                  .home {background-color: darkred;}
                  li a:hover {
                  background-color: #405d27;
                  legend {font-size: 25px; font-style: italic;} p {margin-bottom: 0}
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

                  (dom/form
                    (dom/props {:class "form"
                                :il    "FORM"
                                })
                    (dom/fieldset
                      (dom/props {:name "FORM"
                                  })
                      (dom/legend
                        (dom/text "POMS Signup")
                        (dom/props {:style {:text-align "center"}}))
                      (dom/p (dom/text "username:" (dom/props {:class "text"
                                                               }))
                             (ui/input username
                                       (e/fn [v] (swap! !state assoc :in v))
                                       (dom/on "keyup" (e/fn [keyup]
                                                             (when-some [givenValue (contrib.str/empty->nil (->
                                                                                                              keyup
                                                                                                              .-target
                                                                                                              .-value))]
                                                               (swap! !state assoc :button1 givenValue)
                                                               )))
                                       (dom/props {:style {:name "username"}})
                                       )
                             )
                      (dom/p (dom/text "password:" (dom/props {:class "text"
                                                               }))
                             (ui/input password
                                       (e/fn [v] (swap! !state assoc :in v))
                                       (dom/on "keyup" (e/fn [keyup]
                                                             (when-some [givenValue (contrib.str/empty->nil (->
                                                                                                              keyup
                                                                                                              .-target
                                                                                                              .-value))]
                                                               (swap! !state assoc :button1 givenValue)
                                                               )))
                                       (dom/props {:style {:name "password"}})
                                       ))
                      (dom/p (dom/text "formal name:" (dom/props {:class "text"
                                                                  }))
                             (ui/input formal-name
                                       (e/fn [v] (swap! !state assoc :in v))
                                       (dom/on "keyup" (e/fn [keyup]
                                                             (when-some [givenValue (contrib.str/empty->nil (->
                                                                                                              keyup
                                                                                                              .-target
                                                                                                              .-value))]
                                                               (swap! !state assoc :button1 givenValue)
                                                               )))
                                       (dom/props {:style {:name "formal-name"}})
                                       ))
                      (dom/p (dom/text "formal surname:" (dom/props {:class "text"
                                                                     }))
                             (ui/input formal-surname
                                       (e/fn [v] (swap! !state assoc :in v))
                                       (dom/on "keyup" (e/fn [keyup]
                                                             (when-some [givenValue (contrib.str/empty->nil (->
                                                                                                              keyup
                                                                                                              .-target
                                                                                                              .-value))]
                                                               (swap! !state assoc :button1 givenValue)
                                                               )))
                                       (dom/props {:style {:name "formal-surname"}})
                                       ))
                      (dom/p (dom/text "brand name:" (dom/props {:class "text"
                                                                 }))
                             (ui/input brand-name
                                       (e/fn [v] (swap! !state assoc :in v))
                                       (dom/on "keyup" (e/fn [keyup]
                                                             (when-some [givenValue (contrib.str/empty->nil (->
                                                                                                              keyup
                                                                                                              .-target
                                                                                                              .-value))]
                                                               (swap! !state assoc :button1 givenValue)
                                                               )))
                                       (dom/props {:style {:name "brand-name"}})
                                       ))
                      (dom/p (dom/text "email:" (dom/props {:class "text"
                                                            }))
                             (ui/input email
                                       (e/fn [v] (swap! !state assoc :in v))
                                       (dom/on "keyup" (e/fn [keyup]
                                                             (when-some [givenValue (contrib.str/empty->nil (->
                                                                                                              keyup
                                                                                                              .-target
                                                                                                              .-value))]
                                                               (swap! !state assoc :button1 givenValue)
                                                               )))
                                       (dom/props {:style {:name "email"}})
                                       ))
                      (dom/p (dom/text "phonenumber:" (dom/props {:class "text"
                                                                  }))
                             (ui/input phonenumber
                                       (e/fn [v] (swap! !state assoc :in v))
                                       (dom/on "keyup" (e/fn [keyup]
                                                             (when-some [givenValue (contrib.str/empty->nil (->
                                                                                                              keyup
                                                                                                              .-target
                                                                                                              .-value))]
                                                               (swap! !state assoc :button1 givenValue)
                                                               )))
                                       (dom/props {:style {:name "phonenumber"}})
                                       ))


                      (dom/h4 (dom/props {:class "tedariktxt" :name "ttxt"}) (dom/text "Urun tedarik kategorisi seciniz..."))
                      (dom/div (dom/props {:class "cbborderdiv"})
                               (dom/div
                                 (dom/props {:class "cbdiv"})
                                 (dom/span   (dom/text "henlo" (dom/props {:class "cbtext" :name "tx1"})))
                                 (ui/checkbox
                                   (e/fn [v]
                                         (println "hello world"))
                                   (dom/props {:id "id"  :style {:font-size "10x"}}))
                                 )
                               (dom/div
                                 (dom/props {:class "cbdiv"})
                                 (dom/span   (dom/text "henlo" (dom/props {:class "cbtext" :name "tx1"})))
                                 (ui/checkbox
                                   (e/fn [v]
                                         (println "hello world"))
                                   (dom/props {:id "id" :style {:font-size "10x"}}))
                                 )
                               (dom/div
                                 (dom/props {:class "cbdiv"})
                                 (dom/span (dom/text "henlo" (dom/props {:class "cbtext" :name "tx1"})))
                                 (ui/checkbox
                                   (e/fn [v]
                                         (println "hello world"))
                                   (dom/props {:id "id" :style {:font-size "10x"}}))
                                 )
                               (dom/div
                                 (dom/props {:class "cbdiv"})
                                 (dom/span (dom/text "henlo" (dom/props {:class "cbtext" :name "tx1"})))
                                 (ui/checkbox
                                   (e/fn [v]
                                         (println "hello world"))
                                   (dom/props {:id "id" :style {:font-size "10x"}}))
                                 )
                               )




                      (ui/button
                        (e/fn [] (swap! !state assoc
                                        :login-outh (e/server (e/client (get (e/watch !state) :in)) (e/client (get (e/watch !state) :in2)) db)
                                        ))
                        (dom/text "Signup")
                        (dom/props {:class "signup"
                                    :name  "signup-button"
                                    :style {:text-align "center"}
                                    }))
                      )
                    )
                  ;;router kullanım örnek syntaxı ---->
                  (router/link [`app.poms-01-login-20230828/login-page] (dom/text "login sayfası"))
                  )
                )
              )
            )
          )
        )
