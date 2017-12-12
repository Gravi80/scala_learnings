package actors

import akka.actor.{Actor, ActorSystem, Props}
// actor system is hierarchy

class HActor1 extends Actor{
  override def preStart = {
    context.actorOf(Props[MActor2], "actor2")
  }
  def receive = {
    case _ =>
  }
}

class HActor2 extends Actor{
  override def preStart = {println("my path is: " + context.self.path)}
  def receive = {
    case _ =>
  }
}

object FutureTesting {
  def main(args: Array[String]) {
    val sys = ActorSystem("test")
    implicit val ec = sys.dispatcher

    //Starting an Actor2 from system
    sys.actorOf(Props[HActor2], "actor2")

    //Starting an Actor1 from system which in turn starts an Actor2
    sys.actorOf(Props[HActor1], "actor1")
  }
}

//my path is: akka://test/user/actor2
//Actor2 path is: akka://test/user/actor1/actor2


// There are 2 instances of Actor2 running in test Actorsystem;
// 1). one spawned directly from sys tied to /user/actor2 as it's lookup path and
// 2). one started from an instance of Actor1 tied to /user/actor1/actor2 for its path.