/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
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

package org.optaplanner.examples.cloudbalancing.persistence

import java.io.File
import java.math.BigInteger
import java.util.ArrayList
import java.util.Random

import org.optaplanner.examples.cloudbalancing.domain.CloudBalance
import org.optaplanner.examples.cloudbalancing.domain.CloudComputer
import org.optaplanner.examples.cloudbalancing.domain.CloudProcess
import org.optaplanner.examples.common.app.LoggingMain
import org.optaplanner.examples.common.persistence.AbstractSolutionImporter
import org.optaplanner.examples.common.persistence.SolutionDao

class CloudBalancingGenerator : LoggingMain {

    private class Price constructor(val hardwareValue: Int, val description: String, val cost: Int)

    protected val solutionDao: SolutionDao?
    protected val outputDir: File?
    protected var random: Random = Random(47)

    constructor() {
        checkConfiguration()
        solutionDao = CloudBalancingDao()
        outputDir = File(solutionDao.dataDir, "unsolved")
    }

    constructor(withoutDao: Boolean) {
        if (!withoutDao) {
            throw IllegalArgumentException("The parameter withoutDao ($withoutDao) must be true.")
        }
        checkConfiguration()
        solutionDao = null
        outputDir = null
    }

    fun generate() {
        writeCloudBalance(2, 6)
        writeCloudBalance(3, 9)
        writeCloudBalance(4, 12)
        //        writeCloudBalance(5, 15);
        //        writeCloudBalance(6, 18);
        //        writeCloudBalance(7, 21);
        //        writeCloudBalance(8, 24);
        //        writeCloudBalance(9, 27);
        //        writeCloudBalance(10, 30);
        //        writeCloudBalance(11, 33);
        //        writeCloudBalance(12, 36);
        //        writeCloudBalance(13, 39);
        //        writeCloudBalance(14, 42);
        //        writeCloudBalance(15, 45);
        //        writeCloudBalance(16, 48);
        //        writeCloudBalance(17, 51);
        //        writeCloudBalance(18, 54);
        //        writeCloudBalance(19, 57);
        //        writeCloudBalance(20, 60);
        writeCloudBalance(100, 300)
        writeCloudBalance(200, 600)
        writeCloudBalance(400, 1200)
        writeCloudBalance(800, 2400)
        writeCloudBalance(1600, 4800)
    }

    private fun checkConfiguration() {
        if (CPU_POWER_PRICES.size != MEMORY_PRICES.size || CPU_POWER_PRICES.size != NETWORK_BANDWIDTH_PRICES.size) {
            throw IllegalStateException("All price arrays must be equal in length.")
        }
    }

    private fun writeCloudBalance(computerListSize: Int, processListSize: Int) {
        val fileName = determineFileName(computerListSize, processListSize)
        val outputFile = File(outputDir, fileName + ".xml")
        val cloudBalance = createCloudBalance(fileName, computerListSize, processListSize)
        solutionDao!!.writeSolution(cloudBalance, outputFile)
    }

    fun createCloudBalance(computerListSize: Int, processListSize: Int): CloudBalance {
        return createCloudBalance(determineFileName(computerListSize, processListSize),
                computerListSize, processListSize)
    }

    private fun determineFileName(computerListSize: Int, processListSize: Int): String {
        return "$computerListSize  computers- $processListSize processes"
    }

    fun createCloudBalance(inputId: String, computerListSize: Int, processListSize: Int): CloudBalance {
//        random = Random(47)
        val cloudBalance = CloudBalance()
        cloudBalance.id = 0L
        createComputerList(cloudBalance, computerListSize)
        createProcessList(cloudBalance, processListSize)
        assureComputerCapacityTotalAtLeastProcessRequiredTotal(cloudBalance)
        val possibleSolutionSize = BigInteger.valueOf(cloudBalance.computerList!!.size.toLong()).pow(
                cloudBalance.processList!!.size)
        logger.info("CloudBalance {} has {} computers and {} processes with a search space of {}.",
                inputId, computerListSize, processListSize,
                AbstractSolutionImporter.getFlooredPossibleSolutionSize(possibleSolutionSize))
        return cloudBalance
    }

    private fun createComputerList(cloudBalance: CloudBalance, computerListSize: Int) {
        val computerList = ArrayList<CloudComputer>(computerListSize)
        for (i in 0..computerListSize - 1) {
            val computer = CloudComputer()
            computer.id = i.toLong()
         