(ns statement-parser.core
  (:gen-class)
  (:import (org.apache.pdfbox.pdmodel PDDocument)
           (org.apache.pdfbox.text PDFTextStripper))
  (:require [clojure.java.io :as io]
            [clojure.pprint :as pp]
            [instaparse.core :as insta]))

(def bw-parser
  (insta/parser 
    (-> "bw.bnf" (io/resource) (slurp))))
;(def bw-pdf-parser
;  (insta/parser 
;    (-> "bw-pdfbox.bnf" (io/resource) (slurp))))
(defn with-test-bw-pdf
  [callback]
  (with-open [test-bw-pdf (-> "estatement.pdf" (io/resource) (io/file) (PDDocument/load))]
    (callback test-bw-pdf)))
(defn -main
  "I don't do a whole lot."
  [& args]
  (def bw-sample-filename (or (first args) "bw-sample.csv"))
  (def bw-sample-data (-> bw-sample-filename (io/resource) (slurp)))
  (def parsed-data (insta/parse bw-parser bw-sample-data))
  (pp/pprint parsed-data))
