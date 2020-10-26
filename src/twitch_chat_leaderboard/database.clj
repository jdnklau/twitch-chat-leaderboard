(ns twitch-chat-leaderboard.database
  (:require [clojure.java.io :as io]))

(def database (atom {})) ; Call init! to load from file if exists.

(defn init! []
  (if (.exists (io/file "msg-count.db"))
    (reset! database (clojure.edn/read (java.io.PushbackReader.
                                        (io/reader "msg-count.db"))))
    (do (spit "msg-count.db" {})
        (reset! database {}))))

(defn save-db! []
  (spit "msg-count.db" @database))
