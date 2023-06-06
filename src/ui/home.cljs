(ns ui.home
  (:require [c3kit.wire.js :as wjs]
            [clojure.string :as str]
            [node.child-process :as child-process]
            [node.core :as node]
            [reagent.core :as reagent]
            [ui.page :as page]))

(defn format-results [results]
  (->> results
       str/split-lines
       reverse
       (take 100)
       (str/join "\n")))

(defn run-process [command shell-results]
  (when-not (empty? @command)
    (let [[cmd & args] (str/split @command #"\s")
          p          (child-process/spawn cmd args)
          append-out #(swap! shell-results str %)]
      (child-process/on-error p (comp append-out #(str % "\n")))
      (child-process/on-standard-error p append-out)
      (child-process/on-standard-out p append-out))
    (reset! command "")))

(defmethod page/render :home [_]
  (let [command       (reagent/atom "")
        counter       (reagent/atom 0)
        shell-results (reagent/atom "")]
    (fn []
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
        {:on-click #(swap! counter inc)}
        (str "Clicked " @counter " times")]
       [:p
        [:form {:on-submit (wjs/nod-n-do run-process command shell-results)}
         [:input {:type        :text
                  :on-change   #(reset! command (wjs/e-text %))
                  :value       @command
                  :placeholder "type in shell command"}]]]
       [:pre (format-results @shell-results)]])))
