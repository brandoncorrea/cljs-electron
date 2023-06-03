(ns node.electron.crash-reporter
  (:require [node.electron.core :as electron]))

(def crash-reporter (.-crashReporter electron/electron))
(defn start [opts] (.start crash-reporter (clj->js opts)))
