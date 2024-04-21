package com.sumo.experiments.kafka.streams.twitter

import com.sumo.experiments.kafka.streams.twitter.utils.GenericAvroSerde
import com.sumo.experiments.ml.nlp.LanguageClassifier
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.KStreamBuilder
import org.apache.kafka.streams.kstream.Predicate
import org.apache.kafka.streams.processor.WallclockTimestampExtractor
import org.slf4j.LoggerFactory


class TwitterLanguageAnalyzer {

    private lateinit var kafkaStreams: KafkaStreams

    fun run() {

        val streamsConfig = StreamsConfig(properties)

        val kStreamBuilder = KStreamBuilder()

        val classifier = LanguageClassifier()
        classifier.train("twitterTrainingData_clean.csv")

        val languageToKey = { k: GenericRecord, v: GenericRecord ->
            if (v.get("text").toString().isNotBlank()) classifier.classify(v.get("text").toString()) else "unknown"
        }

        val tweetToKeyValue = { lang: String, tweet: GenericRecord -> KeyValue(tweet.get("user"), tweet) }

        val