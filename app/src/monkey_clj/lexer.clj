(ns monkey-clj.lexer
  (:require [clojure.string :refer [split]]
            [monkey-clj.token :as token :refer [->Token]]))

(def letter? [c]
  )

(defprotocol ILexer
  (parse [this]))

(defn -parse [[c & rest]]
  (when c
    (let [token
          (condp = c
            "=" (->Token ::token/assign c)
            "+" (->Token ::token/plus c)
            "(" (->Token ::token/lparen c)
            ")" (->Token ::token/rparen c)
            "{" (->Token ::token/lbrace c)
            "}" (->Token ::token/rbrace c)
            "," (->Token ::token/comma c)
            ";" (->Token ::token/semicolon c)
            (if (letter? c)
              
              (->Token ::token/illegal c))]
      (cons token (-parse rest)))))

(defrecord Lexer [input]
  ILexer
  (parse [{:keys [input]}] (-parse (split input #""))))

(map str (parse (->Lexer "=+(){},;")))