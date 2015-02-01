(defproject pinlog "0.1.0-SNAPSHOT"
  :description "Simple tool that 'pins' the output of tools like nodemon to always start at the top of the terminal window"
  :url "https://github.com/gdanov/pinlog.git/"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2725"]]

  :node-dependencies [[source-map-support "0.2.8"]
											[dateformat "1.0.11"]]

  :plugins [[lein-cljsbuild "1.0.4"]
            [lein-npm "0.4.0"]]

  :source-paths ["src"]

	:main "run.js"
	
  :cljsbuild {
    :builds [{:id "pinlog"
              :source-paths ["src"]
              :compiler {
                :output-to "out/pinlog.js"
                :output-dir "out"
                :target :nodejs
                :optimizations :none
                ;:source-map true
												 }}]})
