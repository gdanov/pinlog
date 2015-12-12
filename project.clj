(defproject pinlog "0.1.5-SNAPSHOT"
  :description "Simple tool that 'pins' the output of tools like nodemon to always start at the top of the terminal window"
  :url "https://github.com/gdanov/pinlog.git/"

  ;;to publish use lein npm pprint to dump the generated package.json
  
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]]

  :npm {:dependencies [[source-map-support "0.4.0"]
                       [dateformat "1.0.12"]]
        :package {:bin {:pinlog "./server.js"}}}

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-npm "0.6.1"]]

  :source-paths ["src"]

	:main "server.js"
	
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
