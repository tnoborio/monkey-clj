(ns monkey-clj.repl
  (:require [monkey-clj.lexer :refer [->Lexer]]))

(def prompt ">> ")

(defn start []
  (while true
    (prn (map str (.parse (->Lexer (read-line)))))))