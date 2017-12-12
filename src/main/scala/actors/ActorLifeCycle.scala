package actors

import akka.actor.SupervisorStrategy.{Restart, Resume}
import akka.actor.{Actor, ActorSystem, OneForOneStrategy, Props}


class ParentActor extends Actor {
  private var number = 0

  def receive = {
    case "CreateChild" =>
      context.actorOf(Props[ChildActor], "child" + number)
      number += 1
  }

  override val supervisorStrategy = OneForOneStrategy(loggingEnabled = false) {
    case ae: ArithmeticException =>
      sender().path.name    // name of the failed child actor
      println("******* Child Resumed **********")
      Resume
    case _: Exception =>
      println("******* Child Restarted **********")
      Restart
  }
}


case class DivideNumber1(x: Int, y: Int)
class ChildActor extends Actor {
  println("Child Created") // Instantiation of the Actor


  def DivideNumber(dividend: Int, divisor: Int): Float = {
    dividend / divisor
  }

  def receive = {
    case "Hello" => println("Bye")
    case "DivideNumber" =>
      val quotient = DivideNumber(4, 0)
      println(s"$quotient")
    case "BadStuff" =>
      println("******* Something Bad Happend **********")
      throw new RuntimeException("Stuff Happened")
  }

  // Good for cleaning up resources, if actor has access to open files, network connection, database connection
  override def preStart() = {
    super.preStart()
    println("preStart")
  }

  override def postStop() = {
    super.postStop()
    println("postStart")
  }

  override def preRestart(reason: Throwable, message: Option[Any]) = {
    super.preRestart(reason, message)
    println("preRestart")
  }

  override def postRestart(reason: Throwable) = {
    super.postRestart(reason)
    println("postRestart")
  }
}

object AlcMain extends App {
  val system = ActorSystem("HierarchySystem")

  val actor = system.actorOf(Props[ParentActor], "Parent1")

  actor ! "CreateChild"
  val child0 = system.actorSelection("/user/Parent1/child0")
  child0 ! DivideNumber1(4,0)
  child0 ! DivideNumber1(4,2)
  child0 ! "Hello"

//  child0 ! "BadStuff"
//  child0 ! "Hello"

  Thread.sleep(1000)
  system.terminate()
}
