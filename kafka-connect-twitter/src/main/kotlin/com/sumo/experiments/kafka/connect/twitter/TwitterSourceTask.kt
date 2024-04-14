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
imp