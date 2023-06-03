(ns node.core)

(defn dirname [] js/__dirname)
(def platform js/process.platform)
(def version js/process.version)
(defn versions [name] ((js->clj js/process.versions) name))
