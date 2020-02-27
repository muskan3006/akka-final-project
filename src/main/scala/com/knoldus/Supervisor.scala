package com.knoldus


import akka.actor.SupervisorStrategy.{Escalate, Stop}
import akka.actor.{Actor, ActorKilledException, ActorLogging, ActorRef, ActorSystem, Cancellable, DeathPactException, OneForOneStrategy, Props, SupervisorStrategy}
import akka.pattern.ask
import akka.routing.RoundRobinPool
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.language.postfixOps

class Supervisor extends Actor with ReadFile with ActorLogging {
  val worker: ActorRef = context.actorOf(RoundRobinPool(Constants.noOfInstance).props(Props[LogAnalysisAkka])
    .withDispatcher("fixed-thread-pool"), name = "worker")

  implicit val timeout: Timeout = Timeout(500.second)

  def receive: Receive = {
    case "total" =>  val list = find
      val total = Future.sequence(list).map(_.last)
      val result = Await.result(total,100 second)
      log.info(s"$result")
      self ! total
    case total: Future[(Int, Int, Int)] => val average = total.map(allTotal => (worker ? allTotal).mapTo[Map[String, Int]]).flatten
      val result = Await.result(average, 100 second)
      log.info(s"$result")

  }

  def find: List[Future[(Int, Int, Int)]] = { getFiles.map(file => (worker ? file).mapTo[(Int,Int,Int)])
  }

  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy(Constants.maxTry, Constants.timeLimit seconds) {
    case _: ActorKilledException => Stop
    case _: DeathPactException => Stop
    case _: Exception => Escalate
  }

}
