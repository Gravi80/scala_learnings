package language

//Scala Option[ T ] is a container for zero or one element of a given type.
//An Option[T] can be either Some[T] or None object, which represents a missing value.

//Some and None are both children of Option,
// your function signature just declares that you're returning an Option that contains some type.
class Option_demo {
  //  It takes a String as a parameter.
  def toInt(in: String): Option[Int] = {
    try {
      //  If it can convert the String to an Int, it does so, returning it as Some(Int).
      Some(Integer.parseInt(in.trim))
    } catch {
      //  If the String can't be converted to an Int, it returns None.
      case e: NumberFormatException => None
    }
  }
}

object option_test extends App {
  private val option_demo = new Option_demo()
  private val res: Option[Int] = option_demo.toInt("3")
  println(res)

  option_demo.toInt("hjkh") match {
    case Some(i) => println(i)
    case None => println("That didn't work.")
  }

  val bag = List("1", "2", "foo", "3", "bar")

  //  Because we've written toInt to return either a Some[Int] or None value, and
  //  flatMap knows how to handle those values
  val sum = bag.flatMap(option_demo.toInt).sum
  println(sum)
}
