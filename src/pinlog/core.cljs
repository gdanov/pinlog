(ns pinlog.core
	(:require [cljs.nodejs :as nodejs]
						[clojure.string :refer [join]]
						[clojure.walk :refer [walk]]))

(nodejs/enable-util-print!)
(def fs (js/require "fs"))

(def dFormat (js/require "dateformat"))

(def default-rules [(fn lein-cljsbuild ([%] some? (re-find #"Compiling \"out.+" %))) ;precaution to trigget only once for multi-source project
                    (fn nodemon [%] (>= (.indexOf % "[nodemon] restarting due to changes...") 0))])

(def rules (atom default-rules))

(let [path (str (js/process.cwd) "/.pinlog")]
  (.readFile fs path
    (fn [err data]
      (if (nil? err)
        (try
          (let [ru (js/eval (.toString data))]
            (swap! rules (fn [& _] ru))
            (println "rules loaded!"))
          (catch js/Object e (do
                               (println "problems parsing rules:" e)
                               (swap! rules (fn [& _] [])))))
        (do
          (println "can't load rules due to:" err)
          (swap! rules (fn [& _] []))))
      (println "active rules:" (map #(.-name %)  @rules))) ) 
  )
 
(defn check-rules [chunk]
  (some #(% chunk) (seq  @rules)))
 
(defn -main []
 
	(.setEncoding js/process.stdin "utf8")

	(.on js/process.stdin "readable"
		(fn []
			(let [chunk (.read js/process.stdin)]
				(when (not (nil? chunk))
					(do (if (check-rules chunk) (println "\033[2J\033[0;0H~~~ " (dFormat (js/Date.) "HH:MM ss"))) 
							(.write js/process.stdout chunk))))))
   
  (.on js/process.stderr "readable"
    (fn [] (.write js/process.stdout (str "ERR:" (.read js/process.stderr))))))

(set! *main-cli-fn* -main)
(println "starting pinning... hope you use me in this way: >start-the-thing | pinlog")



