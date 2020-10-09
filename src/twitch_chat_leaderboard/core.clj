(ns twitch-chat-leaderboard.core
  (:require [twitch-chat-leaderboard.client :as client]
            [twitch-chat-leaderboard.message-handler :as mh])
  (:gen-class))

(defn -main
  [& args]
  (mh/init!)
  (let [client (client/create-client mh/message-counter!)
        chat (.getChat client)]
    (println "Hello, Twitch!")))
