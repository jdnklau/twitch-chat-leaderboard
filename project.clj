(defproject twitch-chat-leaderboard "0.1.0-SNAPSHOT"
  :description "App to keep track of your most active viewers."
  :url "https://github.com/jdnklau/twitch-chat-leaderboard"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]]
  :main ^:skip-aot twitch-chat-leaderboard.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
