package com.knoldus
import scala.concurrent.duration._
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatest.BeforeAndAfterAll
class SupervisorSpec extends TestKit(ActorSystem("SecondaryActorSpec"))
  with AnyWordSpecLike
  with BeforeAndAfterAll
  with ImplicitSender{
  val testingActor: ActorRef = system.actorOf(Props(classOf[Supervisor]),"PrimaryActorTesting")
  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }
  "total " must {
    " return the total of errors,info and warning" in {
      within(10.second) {
        testingActor ! "total"
        val expectedCount = (0,1483,271)
        expectMsg(expectedCount)
      }
    }
  }
}

