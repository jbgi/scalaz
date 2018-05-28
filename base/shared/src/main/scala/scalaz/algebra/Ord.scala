package scalaz
package algebra

import scala.{ Product, Serializable }

import scalaz.core.{ EqAnyRef, EqAnyVal, EqClass }

sealed abstract class Ordering extends Product with Serializable
final case object LT           extends Ordering
final case object GT           extends Ordering
final case object EQ           extends Ordering

trait OrdClass[A] extends EqClass[A] {
  def comp(a: A, b: A): Ordering

  def <(a: A, b: A): Boolean  = comp(a, b) eq LT
  def <=(a: A, b: A): Boolean = comp(a, b) ne GT
  def >(a: A, b: A): Boolean  = comp(a, b) eq GT
  def >=(a: A, b: A): Boolean = comp(a, b) ne LT
}

object OrdClass {
  trait DeriveEqual[A] extends OrdClass[A] {
    final override def equal(first: A, second: A) = comp(first, second) == EQ
  }
}

trait OrdAnyRef[A <: AnyRef] extends EqAnyRef[A] with OrdClass[A] {
  override final protected def valueEqual(a: A, b: A): Boolean = comp(a, b) eq EQ
}

@specialized
trait OrdAnyVal[A <: AnyVal] extends EqAnyVal[A] with OrdClass[A] {
  override final def equal(a: A, b: A): Boolean = comp(a, b) eq EQ
}
