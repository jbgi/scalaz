package scalaz
package data

import scalaz.data.Maybe._

trait MaybeFunctions {
  def empty[A]: Maybe[A] = Constructors.empty
  def just[A](a: A): Maybe[A] = Constructors.just(a)

  def maybe[A, B](n: B)(f: A => B): Maybe[A] => B = _() match {
    case Just(x)  => f(x)
    case _    => n
  }

  def fromOption[A](oa: Option[A]): Maybe[A] = oa.fold(empty[A])(just)
}
