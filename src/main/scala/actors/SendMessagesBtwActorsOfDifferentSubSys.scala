package actors

import akka.actor.{Actor, ActorSystem, Props}
import akka.serialization.Serialization
import com.typesafe.config.{Config, ConfigFactory}

class ActorSubSys1 extends Actor {
  override def preStart(): Unit = {
    println(Serialization.serializedActorPath(self))
  }

  def receive = {

    case "send" =>
      context.actorSelection("akka.tcp://DifferentSubSys2@127.0.0.1:2555/user/actor1").tell("Hello from ActorSubSys1", self)
    case k => println(s"$k")
  }
}

class ActorSubSys2 extends Actor {
  override def preStart(): Unit = {
    println(Serialization.serializedActorPath(self))
  }

  def receive = {
    case x => println(s"$x")
  }
}


object MessagesBtwActorsDifferentSubSys extends App {


  private val sub_sys1_config: Config = ConfigFactory.parseString(
    """
akka {
actor {
provider = "akka.remote.RemoteActorRefProvider"
}
remote {
transport = "akka.remote.netty.NettyRemoteTransport"
netty.tcp {
       hostname = "127.0.0.1"
       port = 2554
     }
}
}
    """.stripMargin)


  private val sub_sys2_config: Config = ConfigFactory.parseString(
    """
akka {
actor {
provider = "akka.remote.RemoteActorRefProvider"
}
remote {
transport = "akka.remote.netty.NettyRemoteTransport"
netty.tcp {
       hostname = "127.0.0.1"
       port = 2555
     }
}
}
    """.stripMargin)


  val system1 = ActorSystem("DifferentSubSys1", sub_sys1_config)
  val system2 = ActorSystem("DifferentSubSys2", sub_sys2_config)
//  COMMAND     PID   USER   FD   TYPE             DEVICE         SIZE/OFF  NODE    NAME
//   java      22192  Ravi   68u  IPv6      0x53747c85a371f7ed      0t0     TCP   127.0.0.1:2554 (LISTEN)
//   java      22192  Ravi   87u  IPv6      0x53747c85a371f2ad      0t0     TCP   127.0.0.1:2555 (LISTEN)


  val actor1 = system1.actorOf(Props[ActorSubSys1], name = "actor1")
  val actor2 = system2.actorOf(Props[ActorSubSys2], name = "actor1")
  actor1 ! "message to actor 1"
  actor2 ! "message to actor 2"
  actor1 ! "send"

//   COMMAND     PID    USER   FD   TYPE             DEVICE        SIZE/OFF   NODE    NAME
//    java      22192   Ravi   88u  IPv6    0x53747c85a371f2ad      0t0       TCP   127.0.0.1:56951->127.0.0.1:2555 (ESTABLISHED)
//    java      22192   Ravi   89u  IPv6    0x53747c85a2ca22ad      0t0       TCP   127.0.0.1:2555->127.0.0.1:56951 (ESTABLISHED)

//  system1.terminate()
//  system2.terminate()
}

