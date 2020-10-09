(ns twitch-chat-leaderboard.core
  (:require [twitch-chat-leaderboard.client :as client])
  (:gen-class))

(defn -main
  [& args]
  (let [client (client/create-client)
        chat (.getChat client)]
        (println "Hello, Twitch!")))
