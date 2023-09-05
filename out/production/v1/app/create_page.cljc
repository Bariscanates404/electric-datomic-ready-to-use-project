(ns app.create-page
  #?(:cljs (:import [goog.math Long]))
  (:require clojure.edn
            contrib.ednish
            [contrib.datomic-m #?(:clj :as :cljs :as-alias) d]
            [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]
            [hyperfiddle.electric-ui4 :as ui4]
            [hyperfiddle.history :as history]))


(e/defn CreateData []
        (e/client
          (dom/text "Create Data Test!")))
