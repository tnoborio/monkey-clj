(ns monkey-clj.core
  (:require [monkey-clj.repl :as repl]))

(defn user []
  (or (System/getenv "USER") ""))

(defn -main [& _]
  (printf "Hello %s! This is the Monkey-clj programming language!\n" (user))
  (println "Feel free to type in commands")
  (repl/start))
