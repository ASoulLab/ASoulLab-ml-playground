package com.sumo.experiments.ml.nlp

import org.jetbrains.spek.api.Spek
import kotlin.test.assertEquals

class LanguageClassifierSpecs : Spek({

    given("Language Classifier") {
        val classifier = LanguageClassifier()
        on("after training and saving model") {
            classifier.train("twitterTrainingData_clean.csv")
            classifier.sa