package scalaz
package std

import scalaz.algebra.OrdAnyVal
import scalaz.std.utils._

trait BooleanInstances {
  implicit val booleanDebug: Debug[Boolean] = toStringDebug[Boolean]
  implicit val booleanEq: Eq[Boolean]       = universalEq[Boolean]

  implicit val booleanOrd: Ord[Boolean] = instanceOf(new OrdAnyVal[Boolean] {
    def comp(a: Boolean, b: Boolean) = (a, b) match {
      case (true, false) => GT
      case (false, true) => LT
      case _             => EQ
    }
  })
}
