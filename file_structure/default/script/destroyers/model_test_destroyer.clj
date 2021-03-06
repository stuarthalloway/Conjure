(ns destroyers.model-test-destroyer
  (:require [conjure.test.util :as util]
            [conjure.util.file-utils :as file-utils]
            [destroyers.fixture-destroyer :as fixture-destroyer]))

(defn
#^{:doc "Prints out how to use the destroy model test command."}
  usage []
  (println "You must supply a model (Like message).")
  (println "Usage: ./run.sh script/destroy.clj model-test <model>"))

(defn
#^{:doc "Destroys the model test file from the given model."}
  destroy-model-test-file [model]
  (if model
    (let [model-unit-test-dir (util/find-model-unit-test-directory)]
      (if model-unit-test-dir
        (let [model-unit-test-file (util/model-unit-test-file model model-unit-test-dir)]
          (if model-unit-test-file
            (let [is-deleted (. model-unit-test-file delete)] 
              (println "File" (. model-unit-test-file getName) (if is-deleted "destroyed." "not destroyed."))
              (file-utils/delete-all-if-empty model-unit-test-dir (util/find-unit-test-directory)))
            (println "Model test file not found. Doing nothing.")))
        (do
          (println "Could not find the model unit test directory.")
          (println "Command ignored."))))
    (usage)))

(defn
#^{:doc "Destroys a model test file for the model name given in params."}
  destroy-model-test [params]
  (destroy-model-test-file (first params)))

(defn
#^{:doc "Destroys all of the files created by the model_test_generator."}
  destroy-all-dependencies [model]
    (destroy-model-test-file model)
    (fixture-destroyer/destroy-all-dependencies model))