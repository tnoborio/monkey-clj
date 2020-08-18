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
   ::minus "-"
   ::bang "!"
   ::asterisk "*"
   ::slash "/"
   
   ::lt "<"
   ::gt ">"
   
   ::comma ","
   ::semicolon ";"
   ::lparen "("
   ::rparen ")"
   ::lbrace "{"
   ::rbrace "}"

   ::func "func"
   ::let "let"
   ::true "true"
   ::false "false"
   ::if "if"
   ::else "else"
   ::return "return"
   
   ::eq "=="
   ::not-eq "!="
   
   ::eof ""})

(defn lookup-ident [ident]
  ((keyword ident) {:fn ::func
                    :let ::let
                    :true ::true
                    :false ::false
                    :if ::if
                    :else ::else
                    :return ::return} ::ident))