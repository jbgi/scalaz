package scalaz
package typeclass

trait TraversableFunctions {
  def sequence[T[_], F[_]: Applicative: ForAllThunkable, A](tfa: T[F[A]])(implicit T: Traversable[T]): F[T[A]] =
    T.sequence(tfa)
}
