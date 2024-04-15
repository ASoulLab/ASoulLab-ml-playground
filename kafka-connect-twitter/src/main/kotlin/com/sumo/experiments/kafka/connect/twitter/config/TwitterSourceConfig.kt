package com.sumo.experiments.kafka.connect.twitter.config

import org.apache.kafka.common.config.ConfigDef.Importance
import org.apache.kafka.common.config.ConfigDef.Type
import org.apache.kafka.common.config.AbstractConfig
import org.apache.kafka.common.config.ConfigDef
import org.slf4j.LoggerFactory.getLogger


class TwitterSourceConfig(props: Map<String, String>) : AbstractConfig(config, props) {
    companion object {
        private val log = getLogger(TwitterSourceConfig::class.java)

        val CONSUMER_KEY_CONFIG = "twitter.consumerkey"
        val CONSUMER_KEY_CONFIG_DOC = "Twitter account consumer key."
        val CONSUMER_SECRET_CONFIG = "twitter.consumersecret"
        val CONSUMER_SECRET_CONFIG_DOC = "Twitter account consumer secret."
        val TOKEN_CONFIG = "twitter.token"
        val TOKEN_CONFIG_DOC = "Twitter account token."
        val SECRET_CONFIG = "twitter.secret"
        val SECRET_CONFIG_DOC = "Twitter account secret."
        val STREAM_TYPE = "stream.type"
        val STREAM_TYPE_DOC = "Twitter stream type (filter or sample)."
        val STREAM_TYPE_FILTER = "filter"
        val STREAM_TYPE_SAMPLE = "sample"
        val STREAM_TYPE_DEFAULT = STREAM_TYPE_FILTER
