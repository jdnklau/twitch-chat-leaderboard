(ns twitch-chat-leaderboard.message-handler-test
  (:import (com.github.twitch4j.chat.events.channel IRCMessageEvent)
           (java.util Optional))
  (:require [clojure.test :refer :all]
            [twitch-chat-leaderboard.message-handler :refer :all]))

(defn msg
  "Creates a test message from `user` containing the specified `text`.
  Optionally, the `command-type` can be specified."
  ([user text] (msg user text "PRIVMSG"))
  ([user text command-type]
   (proxy [IRCMessageEvent] ["" {} {} #{}]
     (getMessage [] (Optional/of text))
     (getUserName [] user)
     (getCommandType [] command-type))))

(deftest counting-messages
  (testing "normal messages"
    (is (= {"jd" 1}
           (count-message {} (msg "jd" "Hello, Twitch!")))
        "Count first message in empty database")
    (is (= {"jd" 2}
           (count-message {"jd" 1} (msg "jd" "Hello, Twitch!")))
        "Count second message"))
  (testing "Non-PRIVMSG commands"
    (is (= {"jd" 1}
           (count-message {"jd" 1} (msg "jd" "" "USERNOTICE")))
        "Do not count USERNOTICE")))
