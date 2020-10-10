(ns twitch-chat-leaderboard.core
  (:require [twitch-chat-leaderboard.client :as client]
            [twitch-chat-leaderboard.message-handler :as mh]
            [cljfx.api :as fx])
  (:gen-class))

(defn create-app
  [on-close-fn]
  (fn [db]
    {:fx/type :stage
     :showing true
     :title "Twitch Chat Leaderboard"
     :width 300
     :height 100
     :on-close-request on-close-fn
     :scene {:fx/type :scene
             :root {:fx/type :v-box
                    :alignment :top-left
                    :children [{:fx/type :label
                                :text (str db)}]}}}))

(defn close-request [event client]
  (.close client)
  (mh/save-db)
  (System/exit 0))

(defn -main
  [& args]
  (mh/init!)
  (let [client (client/create-client mh/message-counter!)
        app (create-app #(close-request %1 client))
        renderer (fx/create-renderer
                  :middleware (fx/wrap-map-desc assoc :fx/type app))]
    (fx/mount-renderer mh/database renderer)))
