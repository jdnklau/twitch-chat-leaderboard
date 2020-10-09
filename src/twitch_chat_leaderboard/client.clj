(ns twitch-chat-leaderboard.client
  (:import
   (com.github.philippheuer.credentialmanager.domain OAuth2Credential)
   (com.github.philippheuer.credentialmanager CredentialManagerBuilder)
   (com.github.philippheuer.events4j.core EventManager)
   (com.github.twitch4j.auth.providers TwitchIdentityProvider)
   (com.github.twitch4j TwitchClientBuilder)
   (com.github.twitch4j.chat TwitchChatBuilder)
   (com.github.twitch4j.chat.events.channel IRCMessageEvent)))

;; Helper function to more conveniently create a functional java interface.
(defn consumer [f]
  (reify java.util.function.Consumer
    (accept [this t]
      (f t))))

;; Testing function
(defn echo-message [event]
  (if (= (.getCommandType event) "PRIVMSG")
    (let [user (.getUserName event)
          msg (.get (.getMessage event))] ;; TODO: Check if optional is empty.
      (println (str user ":") msg))))

(defn create-client []
  (let [oauth (OAuth2Credential. "twitch" (.trim (slurp ".access-token")))
        cm (.build (CredentialManagerBuilder/builder))
        em (EventManager.)]
    (.autoDiscovery em)
    (.onEvent em IRCMessageEvent
              (consumer echo-message))
    (-> (TwitchClientBuilder/builder)
        (.withEventManager em)
        (.withEnableChat true)
        (.withChatAccount oauth)
        (.build))))
