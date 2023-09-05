(ns ;^:dev/always
  app.demo-index
  (:require [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]
            [hyperfiddle.history :as router]
            #?(:clj [datomic.client.api :as dt])))


(e/def conn)
(e/def db)

(def pages
  [`app.poms-01-login-20230828/login-page
   `app.poms-00-signup-20230831/signup-page
   ])


(e/defn Demos []
        (e/server
          (e/client
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

            (e/for [k pages]
                   (dom/div (router/link [k] (dom/text (name k))))))))




