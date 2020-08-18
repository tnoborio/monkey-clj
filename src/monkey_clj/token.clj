(ns monkey-clj.token)

(deftype Token [type literal]
  Object
  (toString [_]
            (str "Token: " (pr-str type literal))))

(def token-types
  {::illegal "illegal"
   ::int "int"
   ::assign "="
   ::plus "+"
   ::comma ","
   ::semicolon ";"
   ::lparen "("
   ::rparen ")"
   ::lbrace "{"
   ::rbrace "}"
   ::func "func"
   ::let "let"})

(defn lookup-ident [ident]
  ((keyword ident) {:fn ::func
                    :let ::let} ::ident))