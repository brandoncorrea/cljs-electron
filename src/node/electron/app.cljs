(ns node.electron.app
  (:require [node.electron.core :as electron]))

(def app (.-app electron/electron))
(defn quit [] (.quit app))
(defn on-window-all-closed [f] (.on app "window-all-closed" f))
(defn on-ready [f] (.on app "ready" f))
