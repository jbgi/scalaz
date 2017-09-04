package scalaz
package typeclass

trait FoldableClass[F[_]] extends Foldable[F]{
  final def foldable: Foldable[F] = this
}

object FoldableClass {
  trait FoldMap[F[_]] extends Alt[FoldMap[F]] { self : Foldable[F] =>
    override def foldMap[A, B: Monoid: Thunkable](fa: F[A])(f: A => B): B
    override def foldRight[A, B: Thunkable](fa: F[A], z: B)(f: (A, B) => B): B  // = TODO implement from foldmap/endo
  }

  trait FoldRight[F[_]] extends Alt[FoldRight[F]] { self : Foldable[F] =>
    override def foldRight[A, B: Thunkable](fa: F[A], z: B)(f: (A, B) => B): B
    override def foldMap[A, B: Monoid](fa: F[A])(f: A => B)(implicit B: Thunkable[B]) = foldRight(fa, Monoid[B].empty)(
      (a, b) => B.thunk(Semigroup[B].append(f(a),b))
    )
  }

  trait Alt[D <: Alt[D]] { self: D => }
}
