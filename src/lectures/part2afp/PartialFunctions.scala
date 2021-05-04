package lectures.part2afp

object PartialFunctions extends App {

  val aFunction = (x: Int) => x + 1 // Funtion1[Int, Int] === Int => Int

  val aFussyFunction = (x: Int) =>
    if (x == 1) 38
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new FunctionNotAppliclableException

  class FunctionNotAppliclableException extends RuntimeException

  val aNicerFussyFunction = (x: Int) => x match {
    case 1 => 38
    case 2 => 56
    case 5 => 999
  }

  // aNicerFussyFunction function is actually the implementation of a function
  // from the domain {1, 2, 5} => Int
  // Now because {1, 2, 5} is a subset of Int, this aNicerFussyFunction function
  // is called a partial function from Int to Int
  // because it accepts only a part of domain as arguments

  val aPartialFunction: PartialFunction[Int, Int] = {
    case 1 => 38
    case 2 => 56
    case 5 => 999
  } // partial function value

  println(aPartialFunction(2))
  // println(aPartialFunction(57231)) // <- crash the program because this number is not defiend in the PartialFunction.

  // Partial Function utilities
  println(aPartialFunction.isDefinedAt(57231))

  // Now, Partial Functions can be lifted to Total Functions returning Options
  val lifted = aPartialFunction.lift // Int => Option[Int]
  println(lifted(2))
  println(lifted(57231))

  val pfChain = aPartialFunction.orElse[Int, Int] {
    case 45 => 67
  }

  println(pfChain(2))
  println(pfChain(45))

  // Partial Function extend normal functions
  val aTotalFunction: Int => Int = {
    case 1 => 99
  }

  // HOF accept partial functions as well
  val aMappedList = List(1,2,3). map {
    case 1 => 38
    case 2 => 78
    case 3 => 1000
  }

  println(aMappedList)

  /*
    Note: Partial Function can only have ONE parameter type
   */


  /*
    Exercises:

    1 - construct a Partial Function instance yourself (anonymous class)
    2 - implment a dumb chatbot as Partial Function
   */


  // answer 1
  val aManualFussyFunction = new PartialFunction[Int, Int] {
    override def apply(x: Int): Int = x match {
      case 1 => 38
      case 2 => 65
      case 5 => 999
    }

    override def isDefinedAt(x: Int): Boolean =
      x == 1 || x == 2 || x == 5
  }

  // answer 2
  val chatbot: PartialFunction[String, String] = {
    case "hello" => "Hi, my name is TVP2018"
    case "goodbye" => "Once you start talking to me, there is not return, human!"
    case "call mom" => "Unable to find your phone without your credit card"
  }
  scala.io.Source.stdin.getLines().map(chatbot).foreach(println)

}


