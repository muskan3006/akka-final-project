package com.knoldus

import akka.actor.{ActorSystem, Cancellable, Props}
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps

class Scheduler {
  def run: Cancellable = {
    implicit val timeout: Timeout = Timeout(100.second)
    val system = ActorSystem("LogAnalyse")
    val supervisor = system.actorOf(Props[Supervisor],"supervisor")

    system.scheduler.scheduleWithFixedDelay(0 milliseconds, 110 seconds, supervisor, "total")
  }
}


