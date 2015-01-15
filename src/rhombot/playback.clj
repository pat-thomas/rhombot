(ns rhombot.playback
  (:require [clojure.core.async :as async]))

(defn handle-msg
  [msg]
  (println (format "msg => %s" msg)))

(defn play-with-wait
  [sound wait notes]
  (doseq [note notes]
    (do (sound note)
        (Thread/sleep wait))))

(defn start-event-loop
  [event-bus]
  (async/go (while true
              (handle-msg (async/<! event-bus))))
  event-bus)

