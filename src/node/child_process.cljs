(ns node.child-process)

(defonce child-process (js/require "child_process"))

(defn spawn [cmd args]
  (.spawn child-process cmd (clj->js (or args []))))

(defn on-error [proc f] (.on proc "error" f))
(defn on-standard-error [proc f] (.on (.-stderr proc) "data" f))
(defn on-standard-out [proc f] (.on (.-stdout proc) "data" f))
