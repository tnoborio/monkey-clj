(ns monkey-clj.lexer
  (:require [monkey-clj.token :as token :refer [->Token]]))

(defn skip-whitespace [input]
  (drop-while #(some (partial = %) #{\space \newline}) input))

(defn letter? [c]
  (let [code (int c)]
    (or (<= (int \a) code (int \z))
        (<= (int \A) code (int \Z))
        (= c \_))))

(defn number? [c]
  (<= (int \0) (int c) (int \9)))

(def char->token-type
  {\= ::token/assign
   \+ ::token/plus
   \( ::token/lparen
   \) ::token/rparen
   \{ ::token/lbrace
   \} ::token/rbrace
   \, ::token/comma
   \; ::token/semicolon})

(defn- default-token [[c & rest]]
  (if-let [type (char->token-type c)]
    [type (str c) rest]))

(defn int-token [[c & rest]]
  (when (number? c)
    (let [[number rest] (split-with number? (cons c rest))
          number (apply str number)]
      [::token/int number rest])))

(defn- ident-token [[c & rest]]
  (when (letter? c)
    (let [[ident rest] (split-with letter? (cons c rest))
          ident (apply str ident)]
      [(token/lookup-ident ident) ident rest])))

(defn- illegal-token [[c & rest]]
  (prn :illegal-token c rest)
  [::token/illegal c rest])

(defmulti -parse class)

(defmethod -parse :default [input]
  (let [input (skip-whitespace input)]
    (if (first (skip-whitespace input))
      (let [[type literal rest]
            (or (default-token input)
                (ident-token input)
                (int-token input)
                (illegal-token　input))]
        (cons (->Token type literal) (-parse rest)))
      [])))

(defmethod -parse String [input]
  (-parse (char-array input)))

(defprotocol ILexer
  (parse [this]))

(defrecord Lexer [input]
  ILexer
  (parse [_]
    (-parse input)))

(map str (parse (->Lexer " =+(){},;")))

