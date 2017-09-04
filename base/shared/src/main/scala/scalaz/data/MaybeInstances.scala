package scalaz
package data

import Prelude._
import scalaz.data.Maybe.Constructors
import typeclass.{MonadClass, TraversableClass}
import typeclass.FoldableClass._

// TODO Rework lazyness
trait MaybeInstances extends MonadClass.Template[Maybe] with TraversableClass[Maybe] with FoldRight[Maybe] with
  ForAllThunkable[Maybe] {

  implicit def monadInstance: Monad[Maybe] = this

  implicit def traversableInstance: Traversable[Maybe] = this

  implicit def forAllThunkable: ForAllThunkable[Maybe] = this

  def thunk[A](thunk: => Maybe[A]): Maybe[A] = Constructors.thunk(thunk)

  override def ap[A, B](ma: Maybe[A])(mf: Maybe[A => B]): Maybe[B] =
    thunk(ma.fold(a => map[A => B, B](mf)(f => f(a)), empty))

  override def flatMap[A, B](ma: Maybe[A])(f: A => Maybe[B]): Maybe[B] =
    thunk(ma.fold(a => f(a), empty))

  override def map[A, B](ma: Maybe[A])(f: A => B): Maybe[B] =
    thunk(ma.fold(a => just(f(a)), empty))

  override def pure[A](a: A): Maybe[A] =
    just(a)

  override def traverse[F[_]: Applicative, A, B](ma: Maybe[A])(f: A => F[B])(implicit F: ForAllThunkable[F]):
  F[Maybe[B]] = F.thunk(
    ma.fold(a => f(a).map(just), empty.pure[F]))

  override def sequence[F[_]: Applicative, A](ma: Maybe[F[A]])(implicit F: ForAllThunkable[F]): F[Maybe[A]] =
    F.thunk(ma.fold(fa => fa.map(just), empty.pure[F]))

  override def foldLeft[A, B](ma: Maybe[A], b: B)(f: (B, A) => B)(implicit B :Thunkable[B]): B = B.thunk(ma.fold(a => f(b, a), b))

  override def foldRight[A, B](ma: Maybe[A], b: B)(f: (A, B) => B)(implicit B :Thunkable[B]): B = B.thunk(ma.fold(a => f(a, b), b))

  override def toList[A](ma: Maybe[A]): List[A] = ma.fold(List(_), Nil)
}
