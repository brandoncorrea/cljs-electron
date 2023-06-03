(ns electron.core
  (:require [node.core :as node]
            [node.electron.app :as app]
            [node.electron.browser-window :as browser-window]
            [node.electron.core]
            [node.electron.crash-reporter :as crash-reporter]))

(def main-window (atom nil))

(defn init-browser []
  (reset! main-window (browser-window/create
                        {:width          800
                         :height         600
                         :webPreferences {:nodeIntegration  true
                                          :contextIsolation false}}))
  ; Path is relative to the compiled js file (main.js in our case)
  (browser-window/load-url @main-window (str "file://" (node/dirname) "/public/index.html"))
  (browser-window/on-closed @main-window #(reset! main-window nil)))

; CrashReporter can just be omitted
(crash-reporter/start
  {:companyName "MyAwesomeCompany"
   :productName "MyAwesomeApp"
   :submitURL   "https://example.com/submit-url"
   :autoSubmit  false})

(app/on-window-all-closed
  #(when-not (= node/platform "darwin")
     (app/quit)))

(app/on-ready init-browser)
