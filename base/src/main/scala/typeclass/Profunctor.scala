package scalaz
package typeclass

trait Profunctor[F[_, _]] {
  def lmap[A, B, C](fab: F[A, B])(ca: C => A): F[C, B]
  def rmap[A, B, C](fab: F[A, B])(bc: B => C): F[A, C]
  def dimap[A, B, C, D](fab: F[A, B])(ca: C => A)(bd: B => D): F[C, D]
}

object Profunctor extends ProfunctorInstances {

  trait LeftRightMap[F[_, _]] extends LeftRightXorDimap[LeftRightMap[F]] { self: Profunctor[F] =>
    def lmap[A, B, C](fab: F[A, B])(ca: C => A): F[C, B] = dimap[A, B, C, B](fab)(ca)(identity)
    def rmap[A, B, C](fab: F[A, B])(bc: B => C): F[A, C] = dimap[A, B, A, C](fab)(identity)(bc)
  }
  trait Dimap[F[_, _]] extends LeftRightXorDimap[Dimap[F]] { self: Profunctor[F] =>
    def dimap[A, B, C, D](fab: F[A, B])(ca: C => A)(bd: B => D): F[C, D] = rmap(lmap(fab)(ca))(bd)
  }
  trait LeftRightXorDimap[D <: LeftRightXorDimap[D]]

  def apply[F[_, _]](implicit F: Profunctor[F]): Profunctor[F] = F

  object syntax extends ProfunctorSyntax
}
