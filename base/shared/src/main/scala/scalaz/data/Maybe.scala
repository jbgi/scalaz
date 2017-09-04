package scalaz
package data
import scalaz.data.Maybe.{Just, StrictMaybe}
import scalaz.typeclass.Liskov.{isa, liftCvf}

sealed trait Maybe[A] {
  final def fold[B](f: A => B, b: => B): B = this() match {
    case Just(a)  => f(a)
    case _              => b
  }
  /** Evaluate this expression to weak head normal form (WHNF), thus enabling pattern-matching */
  def eval: StrictMaybe[A]
  @inline
  final def apply(): StrictMaybe[A] = eval
}

object Maybe extends MaybeFunctions with MaybeInstances with MaybeSyntax {

  private[data] object Constructors {

    final private[this] val empty : Maybe[Nothing] = new Empty with Maybe[Nothing] {}
    def empty[A] : Maybe[A] = liftCvf[Nothing, A, Maybe](isa[Nothing, A]).apply(empty)

    def just[A](a: A): Maybe[A] = new Just[A](a) with Maybe[A]

    def thunk[A](thunk: => Maybe[A]): Maybe[A] = new Thunk[Maybe[A], StrictMaybe[A]](() => thunk) with Maybe[A] {
      override def eval(a: Maybe[A]): StrictMaybe[A] = a.eval
    }
  }

  sealed abstract class StrictMaybe[A]{ self: Maybe[A] =>
    override final def eval: StrictMaybe[A] = this
  }
  sealed abstract case class Empty[A]() extends StrictMaybe[A] { self: Maybe[A] => }
  sealed abstract case class Just[A](a: A) extends StrictMaybe[A] { self : Maybe[A] => }

  class Unapply[A](val sMaybe: StrictMaybe[A]) extends AnyVal {
    def isEmpty: Boolean = false
    def get: StrictMaybe[A] = sMaybe
  }

  def unapply[A](maybe: Maybe[A]): Unapply[A] = new Unapply(maybe.eval)

}



