package com.itis.testhelper.repository

import com.itis.testhelper.model.Step

interface StepsRepository {

    fun addStep(step: Step)

    fun getSteps(): List<Step>

    fun clearSteps()
}