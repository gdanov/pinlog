(ns pinlog.core
	(:require [cljs.nodejs :as nodejs]
						[clojure.string :refer [join]]
						[clojure.walk :refer [walk]]))

(nodejs/enable-util-print!)

(def dFormat (js/require "dateformat"))

(defn rules [chunk]
	(walk
		#(% chunk)
		#(some identity %)
		[
		 #(not (nil? (re-find #"Compiling \"out.+" %)))           ;precaution to trigget only once for multi-source project
		 #(>= (.indexOf % "[nodemon] restarting due to changes...") 0)
		 ]))

(println "test" (rules "Compiling \"out/pinlog.js\" from [\"src\"]..."))


(defn -main []
	;(println "Hello world!\ntralalalalaa") 

	(.setEncoding js/process.stdin "utf8")

	(.on js/process.stdin "readable"
		(fn []
			(let [chunk (.read js/process.stdin)]

				(if (not (nil? chunk))
					(do (if (rules chunk) (println "\033[2J\033[0;0H~~~ " (dFormat (js/Date.) "HH:MM ss")))
							(.write js/process.stdout (join ["" chunk])))
					))))
	)

(set! *main-cli-fn* -main)