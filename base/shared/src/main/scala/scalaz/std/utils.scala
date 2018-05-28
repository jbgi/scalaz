package scalaz
package std

import scalaz.core.{ EqAnyRef, EqAnyVal, EqClass }
import scalaz.debug.DebugClass

private[std] object utils {
  def singletonEq[A]: Eq[A]                 = instanceOf[EqClass[A]]((a, b) => true)
  def universalEq[A]: Eq[A]                 = instanceOf[EqClass[A]]((a, b) => a == b)
  def universalEqAnyVal[A <: AnyVal]: Eq[A] = instanceOf((_ == _): EqAnyVal[A])
  def universalEqAnyRef[A <: AnyRef]: Eq[A] = instanceOf((_.equals(_)): EqAnyRef[A])
  def toStringDebug[A]: Debug[A]            = instanceOf[DebugClass[A]](a => a.toString)
}
