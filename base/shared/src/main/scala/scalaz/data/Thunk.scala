package scalaz.data

import scala.annotation.tailrec

/** For implementation artifact of lazy data type constructors. Not to be used/exposed directly as type. */
abstract class Thunk[A, B](var thunk: () => A) { self: A  =>
  lazy val eval: B = {
    val value0 = Thunk.run(this)
    thunk = null
    value0
  }
  def eval(a: A): B
}

private object Thunk {
  @tailrec
  def run[A, B](byNeed: Thunk[A, B]): B = {
    val e = byNeed.thunk
    if (e eq null)
      byNeed.eval
    else
      e() match {
        case nested: Thunk[A, B] @unchecked => run(nested)
        case a => byNeed.eval(a)
      }
  }
}