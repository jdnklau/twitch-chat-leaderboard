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

(defn create-client [msg-handler]
  (let [oauth (OAuth2Credential. "twitch" (.trim (slurp ".access-token")))
        cm (.build (CredentialManagerBuilder/builder))
        em (EventManager.)]
    (.autoDiscovery em)
    (.onEvent em IRCMessageEvent
              (consumer msg-handler))
    (-> (TwitchClientBuilder/builder)
        (.withEventManager em)
        (.withEnableChat true)
        (.withChatAccount oauth)
        (.build))))
