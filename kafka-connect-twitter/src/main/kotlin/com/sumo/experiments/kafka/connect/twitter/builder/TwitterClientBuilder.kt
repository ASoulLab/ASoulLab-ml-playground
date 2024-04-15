package com.sumo.experiments.kafka.connect.twitter.builder

import com.sumo.experiments.kafka.connect.twitter.batch
import com.sumo.experiments.kafka.connect.twitter.config.TwitterSourceConfig
import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.core.Constants
import com.twitter.hbc.core.endpoint.Location
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint
import com.twitter.hbc.core.processor.StringDelimitedProcessor
import com.twitter.hbc.httpclient.BasicClient
import com.t