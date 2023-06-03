(ns node.electron.browser-window
  (:require [node.electron.core :as electron]))

(def browser-window (.-BrowserWindow electron/electron))
(defn create [opts] (browser-window. (clj->js opts)))

(defn load-url [window url]
  (.loadURL ^js/electron.BrowserWindow window url))

(defn on-closed [window f]
  (.on ^js/electron.BrowserWindow window "closed" f))
