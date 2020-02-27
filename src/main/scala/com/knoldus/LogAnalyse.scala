package com.knoldus

import com.typesafe.scalalogging.LazyLogging

/**
 * LogAnalyse extends App and execution start from here. In this class I have created object of
 * class LogAnalysis and class LogAnalysisAkka to access their methods
 */
object LogAnalyse extends App with LazyLogging {

  val logAnalysis = new LogAnalysis
  val total = logAnalysis.totalCount
  logger.info(s"$total")
  logger.info(s"${logAnalysis.average(total)}")

  val scheduler = new Scheduler
  scheduler.run

}
