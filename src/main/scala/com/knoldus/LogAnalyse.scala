package com.knoldus

import com.typesafe.scalalogging.LazyLogging

object LogAnalyse extends App with LazyLogging {

  val logAnalysis = new LogAnalysis
  val total = logAnalysis.totalCount
  logger.info(s"$total")
  logger.info(s"${logAnalysis.average(total)}")

  val scheduler = new Scheduler
  scheduler.run

}
