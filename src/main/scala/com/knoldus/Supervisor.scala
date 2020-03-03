package com.knoldus


import akka.actor.SupervisorStrategy.{Escalate, Stop}
import akka.actor.{Actor, ActorKilledException, ActorLogging, ActorRef, DeathPactException, OneForOneStrategy, Props, SupervisorStrategy}
import akka.pattern.{ask, pipe}
import akka.routing.RoundRobinPool
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps

/**
 * Supervisor class is used to implement the concept of supervisor
 */
class Supervisor extends Actor with ReadFile with ActorLogging {
  val worker: ActorRef = context.actorOf(RoundRobinPool(Constants.noOfInstance).props(Props[LogAnalysisAkka])
    .withDispatcher("fixed-thread-pool"), name = "worker")

  implicit val timeout: Timeout = Timeout(500.second)

  /**
   * A receive method that analyses the message sent by an actor
   *
   * @return
   */
  def receive: Receive = {
    case "total" => val list = find
      val total = Future.sequence(list).map(_.last).pipeTo(sender())
         total.map(result =>  log.info(s"$result"))
      self ! total
    case total: Future[(Int, Int, Int)] => val average = total.map(allTotal => (worker ? allTotal).mapTo[Map[String, Int]]).flatten

     average.map(result => log.info(s"$result"))

  }

  /**
   * find method uses the router actor to ask and then do the functioning of getting the total of errors,info and warnings
   *
   * @return
   */
  def find: List[Future[(Int, Int, Int)]] = {
    getFiles.map(file => (worker ? file).mapTo[(Int, Int, Int)])
  }

  /**
   * overriden method supervisionStrategy for applying supervision strategy
   *
   * @return
   */
  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy(Constants.maxTry, Constants.timeLimit seconds) {
    case _: ActorKilledException => Stop
    case _: DeathPactException => Stop
    case _: Exception => Escalate
  }

}
