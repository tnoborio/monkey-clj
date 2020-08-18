(ns monkey-clj.lexer-test
  (:require [clojure.test :refer :all]
            [monkey-clj.token :as token]
            [monkey-clj.lexer :refer :all]))

(deftest lexer
  (testing "parse base tokens"
    (let [input "let five = 5;
                 let ten = 10;
                 
                 let add = fn(x, y) {
                   x + y;
                 };
                 
                 let result = add(five, ten);
                 "
          lexer (->Lexer input)
          tokens (parse lexer)]
      (doseq [[token [expected-type expected-literal]]
              (map vector
                   tokens
                   [[::token/let "let"]
                    [::token/ident "five"]
                    [::token/assign  "="]
                    [::token/int  "5"]
                    [::token/semicolon ";"]

                    [::token/let "let"]
                    [::token/ident "ten"]
                    [::token/assign  "="]
                    [::token/int  "10"]
                    [::token/semicolon ";"]

                    [::token/let "let"]
                    [::token/ident "add"]
                    [::token/assign  "="]
                    [::token/func  "fn"]
                    [::token/lparen "("]
                    [::token/ident "x"]
                    [::token/comma ","]
                    [::token/ident "y"]
                    [::token/rparen  ")"]
                    [::token/lbrace "{"]
                    [::token/ident "x"]
                    [::token/plus "+"]
                    [::token/ident "y"]
                    [::token/semicolon ";"]
                    [::token/rbrace "}"]
                    [::token/semicolon ";"]

                    [::token/let "let"]
                    [::token/ident "result"]
                    [::token/assign  "="]
                    [::token/ident "add"]
                    [::token/lparen "("]
                    [::token/ident "five"]
                    [::token/comma ","]
                    [::token/ident "ten"]
                    [::token/rparen  ")"]
                    [::token/semicolon ";"]

                    [::token/eof ""]])]
        (is (= (.type token) expected-type))
        (is (= (.literal token) expected-literal)))))

  (testing "parse replenished tokens"
    (let [input "!-/*5;
                 5 < 10 > 5;
                 
                 if (5 < 10) {
                     return true;
                 } else {
                     return false;
                 }
                 
                 5 == 10;
                 10 != 9;
                 "
          lexer (->Lexer input)
          tokens (parse lexer)]
      (doseq [[token [expected-type expected-literal]]
              (map vector
                   tokens
                   [[::token/bang "!"]
                    [::token/minus "-"]
                    [::token/slash "/"]
                    [::token/asterisk "*"]
                    [::token/int "5"]
                    [::token/semicolon ";"]

                    [::token/int "5"]
                    [::token/lt "<"]
                    [::token/int "10"]
                    [::token/gt ">"]
                    [::token/int "5"]
                    [::token/semicolon ";"]

                    [::token/if "if"]
                    [::token/lparen "("]
                    [::token/int "5"]
                    [::token/lt "<"]
                    [::token/int "10"]
                    [::token/rparen  ")"]
                    [::token/lbrace "{"]
                    [::token/return "return"]
                    [::token/true "true"]
                    [::token/semicolon ";"]
                    [::token/rbrace "}"]
                    [::token/else "else"]
                    [::token/lbrace "{"]
                    [::token/return "return"]
                    [::token/false "false"]
                    [::token/semicolon ";"]
                    [::token/rbrace "}"]

                    [::token/int "5"]
                    [::token/eq "=="]
                    [::token/int "10"]
                    [::token/semicolon ";"]

                    [::token/int "10"]
                    [::token/not-eq "!="]
                    [::token/int "9"]
                    [::token/semicolon ";"]
                    
                    [::token/eof ""]])]
        (is (= (.type token) expected-type))
        (is (= (.literal token) expected-literal))))))