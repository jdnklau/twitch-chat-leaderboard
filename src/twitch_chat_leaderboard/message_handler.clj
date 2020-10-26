(ns twitch-chat-leaderboard.message-handler
  (:require [twitch-chat-leaderboard.database :refer :all]
            [clojure.java.io :as io]))

(defn count-message
  "Increments the messages sent count in the `db` by one for the respective
   `msg-event`'s author.
   `db` is simply a mapping of user names to their message counts,
   `msg-event` is an IRCMessageEvent."
  [db msg-event]
  (let [user (.getUserName msg-event)
        ; message (.get (.getMessage msg-event)) ;; TODO: Check for value.
        command-type (.getCommandType msg-event)]
    (if (= command-type "PRIVMSG")
      (update db user #(if % (inc %) 1))
      db)))

(defn message-counter! [event]
  (swap! database count-message event))
