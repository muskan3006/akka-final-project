package com.knoldus

import java.io.File

import akka.actor.{Actor, ActorLogging}
import akka.pattern.pipe

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.postfixOps

class LogAnalysisAkka extends LogAnalysis with Actor with ActorLogging {
  var errorCount = 0
  var infoCount = 0
  var warnCount = 0

  def receive: Receive = {
    case path: File => val a = Helper.countError(path)
      val b = Helper.countInfo(path)
      val c = Helper.countWarn(path)
      errorCount = errorCount + a
      infoCount = infoCount + b
      warnCount = warnCount + c
      Future((errorCount, warnCount, infoCount)).pipeTo(sender())
    case allTotal: (Int, Int, Int) => val avg = averageOfValues(allTotal)
      Future(avg).pipeTo(sender())
    case _ => log.info("No file found")

  }

  def averageOfValues(total: (Int, Int, Int)): Map[String, Int] = {
    val noOfFiles = getFiles.length
    val avgErrors = total._1 / noOfFiles
    val avgInfo = total._2 / noOfFiles
    val avgWarn = total._3 / noOfFiles
    Map("averageErrors" -> avgErrors, "averageInfo" -> avgInfo, "averageWarn" -> avgWarn)
  }


}
