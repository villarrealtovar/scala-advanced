package lectures.part2afp

object LazyEvaluation extends App{

  // lazy DELAYS the evaluation of values
  lazy val x: Int = throw new RuntimeException
  // println(x)

  lazy val x_2: Int = {
    println("hello")
    42
  }
  println(x_2)
  println(x_2)

  // examples of implications:

  // 1. side effect
  def sideEffectCondition: Boolean = {
    println("Boolean")
    true
  }

  def simpleCondition: Boolean = false

  lazy val lazyCondition = sideEffectCondition
  println(if (simpleCondition && lazyCondition) "yes" else "no")

  // in conjunction with call by name
  def byNameMethod(n: => Int): Int = n + n + n + 1

  def byNameMethod_2(n: => Int): Int = {
    println("call by need technique")
    lazy val t = n
    t + t + t + 1
  }

  def retrieveMagicValue = {
    // side effect or long computation
    Thread.sleep(1000)
    println("Waiting")
    38
  }

  println(byNameMethod(retrieveMagicValue))

  // use lazy vals
  println(byNameMethod_2(retrieveMagicValue))

  // 3. filtering with lazy values
  def lessThan30(i: Int): Boolean = {
    println(s"$i is less than 30?")
    i < 30
  }

  def greaterThan20(i: Int): Boolean = {
    println(s"$i is greater than 20?")
    i > 20
  }

  val numbers = List(1,25, 40, 5, 23)
  val lt30 = numbers.filter(lessThan30) // List(1,25,5, 23)
  val gt20 = lt30.filter(greaterThan20)
  println(gt20)


  val lt30Lazy = numbers.withFilter(lessThan30) // withFilter is a function on collections that uses lazy values under the hood
  val gt20Lazy = lt30Lazy.withFilter(greaterThan20)
  println("--------------")
  println(gt20Lazy)
  gt20Lazy.foreach(println)

  // for-comprehensions use withFilter with guards
  for {
    a <- List(1,2,3) if a % 2 == 0 // use lazy vals!!
  } yield a + 1

  List(1,2,3).withFilter(_ % 2 == 0).map(_ + 1) // List[Int]

  /*
    Exercise: implement a lazily evaluated, single linked STREAM of elements.

    naturals = MyStream.from(1).(x => x + 1) = stream of natural numbers (potentially infinite!)
    naturals.take(100) // lazily evaluate stream of the first 100 naturals (finite stream)

    naturals.take(100).foreach(println) // it's ok because is finite
    naturals.foreach(println) // will crash - because it's infinite

    naturals.map(_ * 2) // stream of all  even numbers (potentially infinite)

   */

}
