(ns twitch-chat-leaderboard.database-test
  (:require [clojure.test :refer :all]
            [twitch-chat-leaderboard.database :refer :all]))

(deftest leader-board-test
  (testing "top 3 players"
    (is (= nil (top-chatters 3 {}))
        "from empty database")
    (is (= [["jd" 20]] (top-chatters 3 {"jd" 20}))
        "one chatter")
    (is (= [["jd" 20] ["ms" 15]] (top-chatters 3 {"jd" 20 "ms" 15}))
        "two chatters")
    (is (= [["jd" 20] ["ms" 15] ["pp" 10]]
           (top-chatters 3 {"jd" 20 "ms" 15 "pp" 10}))
        "three chatters")
    (is (= [["jd" 20] ["ms" 15] ["pp" 10]]
           (top-chatters 3 {"jd" 20 "ms" 15 "pp" 10 "js" 5}))
        "more than three chatters")
    (is (= [["jd" 20] ["ms" 15] ["pp" 10] ["rs" 10]]
           (top-chatters 3 {"jd" 20 "ms" 15 "pp" 10 "rs" 10 "js" 5}))
        "tie for third place")))

(deftest leader-board-map-test
  (testing "top 3 players"
    (is (= nil (top-chatters 3 {}))
        "from empty database")
    (is (= {1 [20 #{"jd"}]} (leader-board 3 {"jd" 20}))
        "one chatter")
    (is (= {1 [20 #{"jd"}] 2 [15 #{"ms"}]} (leader-board 3 {"jd" 20 "ms" 15}))
        "two chatters")
    (is (= {1 [20 #{"jd"}] 2 [15 #{"ms"}] 3 [10 #{"pp"}]}
           (leader-board 3 {"jd" 20 "ms" 15 "pp" 10}))
        "three chatters")
    (is (= {1 [20 #{"jd"}] 2 [15 #{"ms"}] 3 [10 #{"pp"}]}
           (leader-board 3 {"jd" 20 "ms" 15 "pp" 10 "js" 5}))
        "more than three chatters")
    (is (= {1 [20 #{"jd" "dj"}] 3 [15 #{"ms"}]}
           (leader-board 3 {"jd" 20 "dj" 20 "ms" 15 "pp" 10}))
        "tie for first place")
    (is (= {1 [20 #{"jd"}] 2 [15 #{"ms" "qt"}]}
           (leader-board 3 {"jd" 20 "ms" 15 "qt" 15 "pp" 10}))
        "tie for second place")
    (is (= {1 [20 #{"jd"}] 2 [15 #{"ms"}] 3 [10 #{"pp" "rs"}]}
           (leader-board 3 {"jd" 20 "ms" 15 "pp" 10 "rs" 10 "js" 5}))
        "tie for third place")))
