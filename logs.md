## Debug Logs
- Sayfalari olusturdum ve sayfalarda sadece tek bir tane text olacak sekilde calistirdim. Index sayfasi problemsiz calisti.
- Datomic baglantisini yaptim ve proje hatasiz calismaya devam etti.
- `app/create_page.cljc` dosyasinda db'yi electric ile bagladim ve calismaya devam etti.
- Fonsksiyonlari eklemeye devam ettim.
  - En son ogrencinin idsini cekmek icin yazdigim fonksiyon clojure dosyasinda calisiyor. Ama Clojurescript dosyasinda su hatayi veriyor:
    - ```
      #error {
      :cause Query args must include a database
      :data {:cognitect.anomalies/category :cognitect.anomalies/incorrect, :cognitect.anomalies/message Query args must include a database}
      :via
      [{:type clojure.lang.ExceptionInfo
      :message Query args must include a database
      :data {:cognitect.anomalies/category :cognitect.anomalies/incorrect, :cognitect.anomalies/message Query args must include a database}
      :at [datomic.client.api.impl$incorrect invokeStatic impl.clj 51]}]

      ```
  - Fonksiyon:
    - ```clojure
        #?(:clj (defn student-next-id [db]
        (if (empty? (dt/q '[:find (max ?id)
                            :where [_ :student/id ?id]] db))
            101
            (inc (ffirst (dt/q '[:find (max ?id)
                                 :where [_ :student/id ?id]] db))))))
        ```
  Burdaki hata script icerisinde iki tane datomic modulu eklendi. Bunlar `[contrib.datomic-m #?(:clj :as :cljs :as-alias) d]` ve `#?(:clj [datomic.client.api :as dt])`. Connection baglantisinda `contrib.datomic-m` kullanirken database baglantisin icin `datomic.client.api` kullanmam gerekiyormus. Ana fonksiyon icerisindeki database binding satirini `datomic.client.api` ile degistirerek sorunu cozdum.
    Eski hali:
  ```clojure
  (e/server
          (binding [conn @(requiring-resolve 'user/datomic-conn)]
            (binding [db (d/db conn)]
            ...
  ```
  Yeni hali:
  ```clojure
  (e/server
          (binding [conn @(requiring-resolve 'user/datomic-conn)]
            (binding [db (dt/db conn)]
            ...
  ```
  
- Sayfanin ana fonksiyonunda `(e/client)` ve `(e/server)` siralamasinin onemli oldugunu fark ettim. `(e/client)` fonksiyonu `(e/server)` fonksiyonunun altinda konumlandirdigimizda hata almiyorken `(e/server)` fonksiyonunu `(e/client)` fonksiyonun altinda konumlandirgimizda kodda hata aldim. Hatasiz calisan hali:
  ```clojure
  (e/server
          (binding [conn @(requiring-resolve 'user/datomic-conn)]
            (binding [db (dt/db conn)]

              (e/client
                (dom/div
                ...
  ```