(ns stringcalc.core
  (:require [clojure.string :as str]
            [stringcalc.NegativesNotAllowedException :refer :all]))

(defn sum-with-delims [delim-str num-str]
  (reduce +
          (let [num-vec
                (map #(Integer/parseInt %)
                     (str/split
                       (reduce #(str/replace %1 %2 "\000")
                               num-str
                               (re-seq #"(?<=\[)[^]]+(?=\])|(?<=//)[^\[]"
                                       delim-str))
                       #"\000"))
                neg-vec (filter neg? num-vec)]
            (if (empty? neg-vec)
              (remove #(> % 1000) num-vec)
              (throw (stringcalc.NegativesNotAllowedException.
                       (apply str (interpose "," neg-vec))))))))

(defn add [in-str]
  (cond
    (.isEmpty in-str) 0
    (.startsWith in-str "//") (sum-with-delims (first (str/split-lines in-str))
                                               (second (str/split-lines in-str)))
    :else (sum-with-delims "[,][\n]" in-str)))
