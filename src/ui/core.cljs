(ns ui.core
  (:require [clojure.string :as string :refer [split-lines]]
            [node.child-process :as child-process]
            [node.core :as node]
            [reagent.core :as reagent :refer [atom]]))

(def join-lines (partial string/join "\n"))

(enable-console-print!)

(defonce state (atom 0))
(defonce shell-result (atom ""))
(defonce command (atom ""))

(defn append-to-out [out]
  (swap! shell-result str out))

(defn run-process []
  (when-not (empty? @command)
    (println "Running command" @command)
    (let [[cmd & args] (string/split @command #"\s")
          p (child-process/spawn cmd (or args []))]
      (child-process/on-error p (comp append-to-out #(str % "\n")))
      (child-process/on-standard-error p append-to-out)
      (child-process/on-standard-out p append-to-out))
    (reset! command "")))

(defn root-component []
  [:div
   [:div.logos
    [:img.electron {:src "img/electron-logo.png"}]
    [:img.cljs {:src "img/cljs-logo.svg"}]
    [:img.reagent {:src "img/reagent-logo.png"}]]
   [:pre "Versions:"
    [:p (str "Node     " node/version)]
    [:p (str "Electron " (node/versions "electron"))]
    [:p (str "Chromium " (node/versions "chrome"))]]
   [:button
    {:on-click #(swap! state inc)}
    (str "Clicked " @state " times")]
   [:p
    [:form
     {:on-submit (fn [^js/Event e]
                   (.preventDefault e)
                   (run-process))}
     [:input#command
      {:type        :text
       :on-change   (fn [^js/Event e]
                      (reset! command
                              ^js/String (.-value (.-target e))))
       :value       @command
       :placeholder "type in shell command"}]]]
   [:pre (join-lines (take 100 (reverse (split-lines @shell-result))))]])

(reagent/render
  [root-component]
  (js/document.getElementById "app-container"))
