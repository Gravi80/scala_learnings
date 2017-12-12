package actors

import akka.actor.{Actor, ActorSystem, Props}

// Actor with default constructor
class HelloActor extends Actor {
  def receive = {
    //    case "hello" => println("hello back at you")
    case k => println(s"$k")
  }
}

// Actor with parametrised constructor
class HelloActor1(myName: String) extends Actor {
  def receive = {
    //    case "hello" => println("hello from %s".format(myName))
    //    case _ => println("'huh?', said %s".format(myName))
    case k => println(s"$k")
  }
}


object Main extends App {
  val system = ActorSystem("HelloSystem")

  // create an Actor instance with actorOf, and this shows the syntax for an Actor whose
  // default constructor takes no arguments.
  val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")
  helloActor ! "HelloActor message 1"
  helloActor ! "HelloActor message 2"

  val system1 = ActorSystem("HelloSystem1")
  //  Creating an Akka Actor with a class constructor that takes arguments
  val helloActor1 = system1.actorOf(Props(new HelloActor1("Fred")), name = "helloactor1")
  helloActor1 ! "HelloActor1 message 1"
  helloActor1 ! "HelloActor1 message 2"
  helloActor ! "HelloActor message 3"
  system.terminate()
  system1.terminate()

}
