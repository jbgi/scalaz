package scalaz
package core

trait EqClass[A] {
  def equal(first: A, second: A): Boolean
}

trait EqAnyRef[A <: AnyRef] extends EqClass[A] {
  final def equal(first: A, second: A): Boolean = (first eq second) || valueEqual(first, second)
  protected def valueEqual(first: A, second: A): Boolean
}

@specialized
trait EqAnyVal[T <: AnyVal] extends EqClass[T]
