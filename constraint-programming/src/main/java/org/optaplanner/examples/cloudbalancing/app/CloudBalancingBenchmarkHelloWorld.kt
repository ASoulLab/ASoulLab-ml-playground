
/*
 * Copyright 2014 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.examples.cloudbalancing.app

import org.optaplanner.benchmark.api.PlannerBenchmark
import org.optaplanner.benchmark.api.PlannerBenchmarkFactory

object CloudBalancingBenchmarkHelloWorld {

    @JvmStatic fun main(args: Array<String>) {
        // Build the PlannerBenchmark
        val plannerBenchmarkFactory = PlannerBenchmarkFactory.createFromXmlResource(
                "org/optaplanner/examples/cloudbalancing/benchmark/cloudBalancingBenchmarkConfig.xml")
        val plannerBenchmark = plannerBenchmarkFactory.buildPlannerBenchmark()

        // Benchmark the problem
        plannerBenchmark.benchmark()

        // Show the benchmark report
        println("\nPlease open the benchmark report in:  \n" + plannerBenchmarkFactory.plannerBenchmarkConfig.benchmarkDirectory.absolutePath)
    }

}