package akka_typed

import akka.actor.Actor

// ActorRef => Immutable and serializable handle to an actor, which may or may not reside on the local host or inside the same ActorSystem.
// An ActorRef can be obtained from an ActorRefFactory, an interface which is implemented by ActorSystem and ActorContext.
// This means actors can be created top-level in the ActorSystem or as children of an existing actor, but only from within that actor.

// When we say that classic actors are untyped we mean that
// the type ActorRef does not convey any information of what types of messages that can be sent via that ActorRef, and
// what type the destination Actor has.

//Akka Typed adds that type parameter to the ActorRef:

//val greeter: ActorRef[Greeter.Command] =
//  ctx.spawn(Greeter.greeterBehavior, "greeter")


// Non typed akka

object Greeter1 {
  case object Greet
  final case class WhoToGreet(who: String)
}

// The actor keeps mutable state in var greeting that can be changed by sending the WhoToGreet message to the actor.
class Greeter1 extends Actor {
  import Greeter1._

  private var greeting = "hello"

  override def receive = {
    case WhoToGreet(who) =>
      greeting = s"hello, $who"
    case Greet =>
      println(greeting)
  }
}

