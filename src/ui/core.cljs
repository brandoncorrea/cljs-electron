(ns ui.core
  (:require [c3kit.wire.js :as wjs]
            [reagent.core :as reagent :refer [atom]]
            [ui.home]
            [ui.page :as page]))

(enable-console-print!)

(reagent/render
  [page/render @page/current]
  (wjs/element-by-id "app-container"))
