package com.sumo.experiments.kafka.connect.twitter

import com.sumo.experiments.kafka.connect.twitter.builder.TwitterClientBuilder
import com.sumo.experiments.kafka.connect.twitter.config.TwitterSourceConfig
import com.sumo.experiments.kafka.connect.twitter.models.TwitterStatus
import com.sumo.experiments.kafka.connect.twitter.models.TwitterUser
import com.twitter.hbc.httpclient.BasicClient
import com.twitter.hbc.twitter4j.Twitter4jStatusClient
import org.apache.kafka.common.utils.AppInfoParser
import org.apache.kafka.connect.data.Schema
import org.apache.kafka.connect.source.SourceRecord
import org.apache.kafka.connect.source.SourceTask
import org.slf4j.LoggerFactory.getLogger
import twitter4j.StallWarning
import twitter4j.Status
import twitter4j.StatusDeletionNotice
import twitter4j.StatusListener
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit
import java.util.jar.Manifest

class TwitterSourceTask : SourceTask() {

    private lateinit var sourceConfig: TwitterSourceConfig
    private lateinit var  twitterStatusClient: Twitter4jStatusClient

    //batch size to take from the queue
    private var batchSize = TwitterSourceConfig.BATCH_SIZE_DEFAULT
    private var batchTimeout = TwitterSourceConfig.BATCH_TIMEOUT_DEFAULT
    //The Kafka topic to append to
    private var topic = TwitterSourceConfig.TOPIC_DEFAULT
    private val rawQueue = LinkedBlockingQueue<String>(10000)
    private val statusQueue = LinkedBlockingQueue<Status>(10000)
    private var statusConverter = TwitterSourceConfig.OUTPUT_FORMAT_DEFAULT

    companion private object {
        val log = getLogger(TwitterSourceTask::class.java)
        val TRACK_TERMS = "track.terms";
        val TWEET_LANG = "lang";
        val TWEET_ID = "tweetId";

        fun statusToStringKeyValue(status: Status, topic: String): SourceRecord {
            return SourceRecord(
                //TODO mapOf(TRACK_TERMS to sourceConfig.getString(TwitterSourceConfig.TRACK_TERMS)), //source partitions?
                mapOf(TWEET_LANG to status.lang), //source partitions?
                mapOf(TWEET_ID to status.id), //source offsets?
                topic,
                null,
                Schema.STRING_SCHEMA,
                status.user.screenName,
                Schema.STRING_SCHEMA,
                status.text
            )
        }

        fun statusToTwitterStatusStructure(status: Status, topic: String): SourceRecord {
            val ts = TwitterStatus(status)
            return SourceRecord(
                mapOf(TWEET_LANG to status.lang), //source partitions?
                mapOf(TWEET_ID to status.id), //source offsets?
                topic,
                null,
                TwitterUser.SCHEMA,
                ts.get("user"),
                ts.schema(),
                ts
            )
        }
    }

    override fun version(): String = TwitterSourceTask::class.java.`package`.implementationVersion

    override fun start(props: Map<String, String>) {

        sourceConfig = TwitterSourceConfig(props)
        batchSize = sourceConfig.getInt(TwitterSourceConfig.BATCH_SIZE)
        batchTimeout = sourceConfig.getDouble(TwitterSourceConfig.BATCH_TIMEOUT)
        topic = sourceConfig.getString(TwitterSourceConfig.TOPIC)
        statusConverter = sourceConfig.getString(TwitterSourceConfig.OUTPUT_FORMAT)

        val twitterBasicClient = TwitterClientBuilder.getTwitterClient(sourceConfig, context, rawQueue)

        twitterStatusClient = Twitter4jStatusClient(
            twitterBasicClient,
            rawQueue,
            listOf(object : StatusListener {
                override fun onScrubGeo(userId: Long, upToStatusId: Long) {
                    log.debug("onScrubGeo $userId $upToStatusId")
                }

                override fun onTrackLimitationNotice(numberOfLimitedStatuses: Int) {
                    log.info("onTrackLimitationNotice $numberOfLimitedStatuses")
                }

                override fun onStallWarning(warning: StallWarning) {
                    log.warn("onStallWarning", warning)
                }

                override fun onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {
                    log.debug("onDeletionNotice", statusDeletionNotice)
                }

                override fun onException(ex: Exception) {
                    log.warn("onException: ", ex)
                }

                override fun onStatus(status: Status) {
//                    log.debug("------------StatusListener-----onStatus-