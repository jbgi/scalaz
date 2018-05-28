package scalaz
package std

import algebra.OrdAnyVal
import utils._

trait IntInstances {
  implicit val intDebug: Debug[Int] = toStringDebug[Int]
  implicit val intEq: Eq[Int]       = universalEq[Int]

  implicit val intOrd: Ord[Int] = instanceOf(new OrdAnyVal[Int] {
    def comp(a: Int, b: Int) = java.lang.Integer.compare(a, b) match {
      case 0          => EQ
      case x if x < 0 => LT
      case _          => GT
    }
  })
}
