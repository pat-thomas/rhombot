(ns rhombot.playback)

(defn play-with-wait
  [sound wait notes]
  (doseq [note notes]
    (do (sound note)
        (Thread/sleep wait))))

