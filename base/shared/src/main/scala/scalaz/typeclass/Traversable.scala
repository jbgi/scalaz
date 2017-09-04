package scalaz
package typeclass

trait Traversable[T[_]] {
  def functor: Functor[T]
  def foldable: Foldable[T]

  def traverse[F[_]: Applicative : ForAllThunkable, A, B](ta: T[A])(f: A => F[B]): F[T[B]]
  def sequence[F[_]: Applicative: ForAllThunkable, A](ta: T[F[A]]): F[T[A]]
}

object Traversable extends TraversableInstances with TraversableSyntax {
  def apply[T[_]](implicit T: Traversable[T]): Traversable[T] = T
}
