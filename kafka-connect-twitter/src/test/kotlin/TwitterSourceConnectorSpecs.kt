import com.nhaarman.mockito_kotlin.mock
import com.sumo.experiments.kafka.connect.twitter.config.TwitterSourceConfig
import com.sumo.experiments.kafka.connect.twitter.TwitterSourceConnector
import com.sumo.experiments.kafka.connect.twitter.TwitterSourceTask
import org.apache.kafka.connect.connector.ConnectorContext
import org.jetbrains.spek.api.Spek
import kotlin.test.assertEquals

class TwitterSourceConnectorSpecs : Spek( {
    given("a TwitterSourceConnector") {
        val sourceConnector = TwitterSourceConnecto