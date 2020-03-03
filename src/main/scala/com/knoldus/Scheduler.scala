package com.knoldus

import akka.actor.{ActorSystem, Cancellable, Props}
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps

/**
 * In class scheduler I have used scheduler so that the log files can be analyzed after every fixed duration
 */
class Scheduler {
  /**
   * The method run is used to implement scheduler
   *
   * @return
   */
  def run: Cancellable = {
    implicit val timeout: Timeout = Timeout(100.second)
    val system = ActorSystem("LogAnalyse")
    val supervisor = system.actorOf(Props[Supervisor], "supervisor")

    system.scheduler.scheduleWithFixedDelay(0 milliseconds, 300 seconds, supervisor, "total")
  }
}


