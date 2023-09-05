(ns ;^:dev/always
  app.demo-index
  (:require [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]
            [hyperfiddle.history :as router]))




(def pages
  [`app.create-page/CreateData])
(e/defn Demos []
        (e/client
          (dom/h1 (dom/text "Student Registration App"))
          (e/for [k pages]
                 (dom/div (router/link [k] (dom/text (name k)))))))
