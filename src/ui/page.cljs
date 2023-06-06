(ns ui.page
  (:require [reagent.core :as reagent]))

(def current (reagent/atom :home))
(defmulti render identity)
