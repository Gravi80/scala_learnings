package futures

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object FutureTest extends App {
  println("This is first")
  val f = Future {
    println("printing in the future")
  }
  Thread.sleep(1)
  println("This is last")
}