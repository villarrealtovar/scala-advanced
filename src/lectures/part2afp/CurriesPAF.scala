package lectures.part2afp

/*
  PAF: Partial Applied Functions
 */
object CurriesPAF extends App {
  // curried functions
  val superAdder: Int => Int => Int =
    x => y => x + y

  val add3 = superAdder(3) // Int => Int = y => 3 + y
  println(add3(5))
  println(superAdder(3)(5)) // curried function

  // METHOD!
  def curriedAdder(x: Int)(y: Int): Int = x + y // curried method

  val add4: Int => Int = curriedAdder(4)
  // lifting = ETA - Expansion

  // functions != methods (JVM limitation)

  def inc(x: Int) = x + 1
  List(1,2,3).map(inc) // compiler does ETA-expansion and lift inc method convert in a lambda
                      // List(1,2,3).map(x => inc(x))

  // Partial function applications
  val add5 = curriedAdder(5) _ // Int => Int

  // Exercises
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y

  // define add7: Int => Int = y => 7 + y
  // as many different implementation of add7 using the above
  // be creative!

  val add7_1 = (x: Int) => simpleAddFunction(7, x) // simplest
  val add7_2 = simpleAddFunction.curried(7)

  val add7_3 = curriedAddMethod(7) _ // PAF
  val add7_4 = curriedAddMethod(7)(_) // PAF = alternative syntax

  val add7_5 = simpleAddMethod(7, _: Int) // alternative syntax for turning methods into function values
                // y => simpleAddMethod(7, y)
  val add7_6 = simpleAddFunction(7, _: Int) // works as well

  // underscores are powerful
  def concatenator(a: String, b: String, c: String) = a + b + c
  val insertName = concatenator("Hello, I'm ", _: String, ", how are you") // x: String => concatenator("hello", x, "how are you?")
  println(insertName("Andres"))

  val fillInTheBlanks = concatenator("Hello, ", _: String, _: String) // (x, y) => concatenator("Hello, ", x, y)
  println(fillInTheBlanks("Jose ", "Scala is awesome"))

  /*
    Exercises

    1. Process a list of numbers and return their string representations with different formats
        Use the %4.2f, %8.6g and %14.12f with a curried formatter function
   */
  println("%4.2f".format(Math.PI))
  println("%8.6f".format(Math.PI))

  def curriedFormatter(s: String)(number: Double): String = s.format(number)
  val numbers = List(Math.PI, Math.E, 1, 9.8, 1.3e-11)

  val simpleFormat = curriedFormatter("%4.2f") _ // lift
  val seriousFormat = curriedFormatter("%8.6f") _
  val preciseFormat = curriedFormatter("%14.12f") _

  println(numbers.map(simpleFormat))
  println(numbers.map(seriousFormat))
  println(numbers.map(preciseFormat))

  println(numbers.map(curriedFormatter("%14.12f"))) // compiler does sweet eta-expansion for us

  /*
    2. difference between
      - function vs methods
      - parameters: by-name vs 0-lambda
   */
  def byName(n: => Int) = n + 1
  def byFunction(f: () => Int) = f() + 1

  def method: Int = 38
  def parenMethod(): Int = 38

  /*
    calling byName and byFunction
      - int
      - method
      - parentMethod
      - lambda
      - PAF
   */
  byName(23) // ok
  byName(method) // ok
  byName(parenMethod())
  byName(parenMethod) // ok, but beware => byName(parenMethod())
  // byName(() => 42) // not ok
  byName((() => 42)()) // ok, because it's called
  // byName(parenMethod _) // not ok


  // byFunction(45) // not ok
  // byFunction(method) // not ok!!!! compiler does not ETA-expansion
  //byFunction(parenMethod()) // not ok
  byFunction(parenMethod) // ok, compiler does ETA-expansion
  byFunction(() => 46) // ok
  byFunction(parenMethod _) // ok, but warning- unnecessary


}
