
/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
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

package org.optaplanner.examples.cloudbalancing.solver.score

import java.util.HashMap
import java.util.HashSet

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator
import org.optaplanner.examples.cloudbalancing.domain.CloudBalance
import org.optaplanner.examples.cloudbalancing.domain.CloudComputer
import org.optaplanner.examples.cloudbalancing.domain.CloudProcess

class CloudBalancingMapBasedEasyScoreCalculator : EasyScoreCalculator<CloudBalance> {

    override fun calculateScore(cloudBalance: CloudBalance): HardSoftScore {
        val computerListSize = cloudBalance.computerList!!.size
        val cpuPowerUsageMap = HashMap<CloudComputer, Int>(computerListSize)
        val memoryUsageMap = HashMap<CloudComputer, Int>(computerListSize)
        val networkBandwidthUsageMap = HashMap<CloudComputer, Int>(computerListSize)
        for (computer in cloudBalance.computerList!!) {
            cpuPowerUsageMap.put(computer, 0)
            memoryUsageMap.put(computer, 0)
            networkBandwidthUsageMap.put(computer, 0)