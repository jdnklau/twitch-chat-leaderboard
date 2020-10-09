(ns twitch-chat-leaderboard.core
  (:import
   (com.github.philippheuer.credentialmanager.domain OAuth2Credential)
   (com.github.philippheuer.credentialmanager CredentialManagerBuilder)
   (com.github.philippheuer.events4j.core EventManager)
   (com.github.twitch4j.auth.providers TwitchIdentityProvider)
   (com.github.twitch4j TwitchClientBuilder)
   (com.github.twitch4j.chat TwitchChatBuilder)
   (com.github.twitch4j.chat.events.channel IRCMessageEvent))
  (:gen-class))

(defn load-token [name]
  (.trim (slurp name)))

(def oauth-token
  (load-token ".access-token"))

(def oauth (OAuth2Credential. "twitch" oauth-token))

;; Credential Manager
(def cm (.build (CredentialManagerBuilder/builder)))

;; EventManager
(def em (EventManager.))

;; Helper function to more conveniently create a functional java interface.
(defn consumer [f]
  (reify java.util.function.Consumer
    (accept [this t]
      (f t))))

(defn echo-message [event]
  (if (= (.getCommandType event) "PRIVMSG")
    (let [user (.getUserName event)
          msg (.get (.getMessage event))] ;; TODO: Check if optional is empty.
      (println (str user ":") msg))))

(defn -main
  [& args]
  (.autoDiscovery em)
  (.onEvent em IRCMessageEvent
            (consumer echo-message))
  (let [client (-> (TwitchClientBuilder/builder)
                   (.withEventManager em)
                   (.withEnableChat true)
                   (.withChatAccount oauth)
                   (.build))
        chat (.getChat client)]
    (println "Hello, Twitch!")))
