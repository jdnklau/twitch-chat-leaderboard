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

(defn top-chatters
  "Returns the top `n` chat users from the `db`."
  [n db]
  (let [sorted (sort-by val > db)]
    (if (empty? sorted)
      nil
      ;; Cannot just use take as we need to account for ties.
      (if-let [[_ v] (nth sorted (dec n) false)]
        (take-while (fn [[_ cnt]] (>= cnt v)) sorted)
        sorted))))

(defn leader-board
  "Returns top `n` chat users from `db` as map. Keys are their rank (1..n).
   Note that ties are accounted for. Values in the map are a of the form
   `[count col]` with col containing the respective user names.
   If `n=4` and two viewers tie for 2nd place, there will not be an entry for
   3rd place. 4th place is distributed again."
  [n db]
  (let [lb (sorted-map)
        top-n (top-chatters n db)]
    (if top-n
      ;; Not empty.
      (loop [lb lb ; {rank #{users...}}
             [top-1 & top-rest] top-n
             rank 0
             next-rank-increment 1
             old-count -1]
        (let [[user count] top-1
              same-rank? (= count old-count)
              new-rank (if same-rank?
                         rank
                         (+ rank next-rank-increment))
              new-rank-increment (if same-rank?
                                   (inc next-rank-increment)
                                   1)
              nlb (update
                   lb new-rank
                   (fn [users]
                     (if users
                       [count (conj (second users) user)]
                       [count #{user}])))]
          (if (empty? top-rest)
            nlb
            (recur nlb top-rest new-rank new-rank-increment count))))
      ;; Empty.
      nil)))
