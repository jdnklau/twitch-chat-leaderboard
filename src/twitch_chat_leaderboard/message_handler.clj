(ns twitch-chat-leaderboard.message-handler
  (:require [clojure.java.io :as io]))

(def database (atom {})) ; TODO: Load from file.

(defn init! []
  (if (.exists (io/file "msg-count.db"))
    (reset! database (clojure.edn/read (java.io.PushbackReader.
                                        (io/reader "msg-count.db"))))
    (do (spit "msg-count.db" {})
        (reset! database {}))))

(defn echo-message [event]
  (if (= (.getCommandType event) "PRIVMSG")
    (let [user (.getUserName event)
          msg (.get (.getMessage event))]
      (println (str user ":") msg))))

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
  (swap! database count-message event)
  (println @database)
  ;; FIXME: Don't save it all the time to disk, only every X seconds.
  (spit "msg-count.db" @database))
