package actors

import akka.actor.{Actor, ActorSystem, Props}
import kamon.Kamon

// When you use context.actorSelection inside of an actor, what you are saying is find an actor under this current
// actors control (started by/supervised by).
// Since actor2 was not started by actor1 (or is not supervised by actor1), then it won't resolve to anything.

// The reason context.system.actorSelection works is because you are going all the way "up" to system first before
// starting your search and fully qualifying the path to the actor.
// System "owns" actor2 if you started it up as system.actorOf, so using that path will allow you to get to it starting
// from system.
class MActor1 extends Actor {
  override def preStart(): Unit = {
    println(self)
  }

  def receive = {
    case "send" =>
      //  The supplied path is parsed as a java.net.URI, it is split on / into path elements.
      // If the path starts with /, it is absolute and the look-up starts at the root guardian (which is the parent of "/user")
      // otherwise it starts at the current actor.;
      //  If a path element equals .., the look-up will take a step “up” towards the supervisor of the currently traversed actor,
      // otherwise it will step “down” to the named child.
      // ".." in actor paths here always means the logical structure, i.e. the supervisor.

      //  ***************** Use actorSelection, then send Identify, then ask *************************
      context.system.actorSelection("/user/mactor2").tell("Hello from MActor 1", self)
    case k => println(s"$k")
  }
}

class MActor2 extends Actor {
  override def preStart(): Unit = {
    println(self)
  }

  def receive = {
    //    case "hello" => println("hello from %s".format(myName))
    //    case 'send' =>
    case x => println(s"$x")
  }
}


object MessagesBtwActors extends App {
  val system = ActorSystem("MessageSystem1")


  //  println(system.settings)

  val actor1 = system.actorOf(Props[MActor1], name = "mactor1")
  val actor2 = system.actorOf(Props[MActor2], name = "mactor2")
  //  akka://MessageSystem1/user/mactor1

  //  ActorSystem are much like Unix system
  //  It creates a few top level Actors -
  // the most important being the root Actor with the path /,
  // the user Actor with the path /user and a
  // system Actor with the path /system.
  //  there's also a /deadLetters that represent the DeadLetterActorRef

  actor1 ! "message to actor 1"


  //  To acquire an ActorRef that is bound to the life-cycle of a specific actor you need to
  // send a message, such as the built-in Identify message, to the actor and
  // use the sender reference of a reply from the actor.
  //  ActorSelection tries to send an Identify message using the ask-pattern (therefore the timeout) to the selection and
  // if it receives a response it will provide the ActorRef from which it got the response.
  Thread.sleep(5000)
  actor1 ! "send"
  system.terminate()
}
