(ns stringcalc.core-test
  (:require [clojure.test :refer :all]
            [stringcalc.core :refer :all]))

(deftest my-test-suite
  (is (= 0 (add "")) "Test for empty strings")
  (is (= 1 (add "1")) "Test for 1 number")
  (is (= 3 (add "1,2")) "Test for 2 numbers")
  (is (= 6 (add "1,2\n3")) "Test for commas AND newlines")
  (is (= 6 (add "//;\n1;2;3")) "Test for custom delimiter")
  (is (thrown-with-msg? stringcalc.NegativesNotAllowedException #"-2,-12"
                        (add "//;\n1;-2;2;-12;3")))
  (is (= 1006 (add "//;\n1;1000;2;3;5000")) "Test for numbers > 1000")
  (is (= 1006 (add "//[;;;][+++][***]\n1;;;1000+++2***3;;;5000"))
      "Test for multiple bracketed delimiters")
  )
