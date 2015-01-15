(ns rhombot.sounds
  (:require [overtone.live :refer :all]))

(definst bell
  [frequency 440
   duration  10
   h0        1
   h1        0.5
   h2        0.3
   h3        0.25
   h4        0.2
   h5        0.16
   h6        0.14]
  (let [harmonic-series [1 2 3 4 4.1 5 5.9 6 7]
        proportions     [h0 h1 h2 h3 h4 h5 h6]
        component       (fn [harmonic proportion]
                          (* 1/2
                             proportion
                             (env-gen (perc 0.01 (* proportion duration)))
                             (- (sin-osc (* harmonic frequency))
                                (* (saw (* harmonic frequency))
                                   (square (* 0.5 harmonic frequency))))))
        whole           (mix (map component harmonic-series proportions))]
    (detect-silence whole :action FREE)
    whole))

(definst buzzer
  [frequency 440
   duration  10
   h0        1
   h1        0.5
   h2        0.3
   h3        0.25
   h4        0.2
   h5        0.16
   h6        0.14]
  (let [harmonic-series [1 2 3 4 4.1 5 5.9 6 7]
        proportions     [h0 h1 h2 h3 h4 h5 h6]
        component       (fn [harmonic proportion]
                          (* 1/2
                             proportion
                             (env-gen (perc 0.01 (* proportion duration)))
                             (- (sin-osc (* harmonic frequency))
                                (+ (saw (* harmonic frequency))
                                   (square (* (* 0.5 harmonic) frequency))))))
        whole           (mix (map component harmonic-series proportions))]
    (detect-silence whole :action FREE)
    whole))

(comment
  (let [notes [440 330 550 220 880]]
    (doseq [note notes]
      (do (buzzer note)
          (Thread/sleep 50))))
  (let [notes [440 330 550 220]]
    (doseq [note notes]
      (do (bell note)
          (Thread/sleep 50))))
  )

(definst kick
  [amp 0.5 decay 0.12 freq 30]
  (* (/ (* (square freq (* Math/PI 0.5))
           (square (* 1.01 freq) (* Math/PI 0.5)))
        (sin-osc (* 0.501 freq) (* Math/PI 0.5)))
     (env-gen (perc 0.1 decay) 1 1 0 1 FREE)
     amp))

(kick)

(comment
  (dotimes [_ 4]
    (doseq [thing (shuffle (concat (repeat 10 true) (repeat 6 false)))]
      (do (if thing
            (kick 0.8 0 (rand-nth [30 60 90 120 150 180 210 240 270])))
          (Thread/sleep 100))))
  )
