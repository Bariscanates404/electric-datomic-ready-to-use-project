(ns user ; Must be ".clj" file, Clojure doesn't auto-load user.cljc

  ;datomic kullanmak için aşağıdaki requireları çağırıyoruz.   -------->
  (:require datomic.client.api
            datomic.client.api.async
            hyperfiddle.rcf)
  ;buraya kadar  ----------------->

  )

; lazy load dev stuff - for faster REPL startup and cleaner dev classpath
(def start-electric-server! (delay @(requiring-resolve 'app.electric-server-java8-jetty9/start-server!)))
(def shadow-start! (delay @(requiring-resolve 'shadow.cljs.devtools.server/start!)))
(def shadow-watch (delay @(requiring-resolve 'shadow.cljs.devtools.api/watch)))

(def electric-server-config
  {:host "0.0.0.0", :port 8080, :resources-path "public"})


;datomic client ve conn objelerini olusturuyoruz ---------->
(def datomic-client)
(def datomic-conn)
;;buraya kadar  -------------->



(defn main [& args]
  (println "Starting Electric compiler and server...")
  (@shadow-start!) ; serves index.html as well
  (@shadow-watch :dev) ; depends on shadow server
  ; Shadow loads app.todo-list here, such that it shares memory with server.
  (def server (@start-electric-server! electric-server-config))
  (comment (.stop server))



  ;inject datomic root bindings
  ;datomic kurulumu için aşağıdaki 4 fonksiyonu buraya ekliyoruz.  ------------------>
  (alter-var-root #'datomic-client (constantly (datomic.client.api/client {:server-type :dev-local
                                                                           ;;storage-dir'ı memoryde değil belirli bir yerde olusturuyoruz schemayı baska bir clojure projesinden olusturup bu dosya yoluna improt ediyorum,
                                                                           ;; gerekirse silip bastan  ekleyebiliriz.
                                                                           :storage-dir "/Users/bariscanates/prj/colabio/poms-db"
                                                                           ;aynı anda birden fazla repl ile dbye bağlanmak istiyorsak system adını değiştirmeliyiz. örneğin "di".
                                                                           :system "ci"})))
  ;db ismi "poms-db" burada olusturulan db ile oluşturduğumuz dbinin ismi aynı olmalı buna dikakt edelim.
  (alter-var-root #'datomic-conn (constantly (datomic.client.api/connect datomic-client {:db-name "poms-db"})))
  (def db (datomic.client.api.async/db datomic-conn))
  (hyperfiddle.rcf/enable!))
;;buraya kadar--------------------------->




; Server-side Electric userland code is lazy loaded by the shadow build.
; WARNING: make sure your REPL and shadow-cljs are sharing the same JVM!
(comment
  (main) ; Electric Clojure(JVM) REPL entrypoint
  (hyperfiddle.rcf/enable!) ; turn on RCF after all transitive deps have loaded
  (shadow.cljs.devtools.api/repl :dev) ; shadow server hosts the cljs repl
  ; connect a second REPL instance to it
  ; (DO NOT REUSE JVM REPL it will fail weirdly)
  (type 1))