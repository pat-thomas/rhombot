(ns rhombot.core
  (:require [rhombot.sounds     :as sounds]
            [rhombot.playback   :as playback]
            [clojure.core.async :as async]))

(defn init
  [{:keys [buffer-size]}]
  (-> buffer-size async/chan playback/start-event-loop))

(comment
  (let [evt-bus (init {:buffer-size 200})]
    (async/>!! evt-bus "foo")
    (async/>!! evt-bus "foof"))
  )
