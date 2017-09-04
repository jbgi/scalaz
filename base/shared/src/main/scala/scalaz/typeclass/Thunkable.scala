package scalaz.typeclass

trait Thunkable[A] {
  def thunk(a: =>A): A
}

trait ForAllThunkable[F[_]] {
  def thunk[A](fa: =>F[A]): F[A]
  final def thunkable[A]: Thunkable[F[A]] = thunk(_)
}

trait ThunkableFunctions {
  final def thunk[A](a: => A)(implicit A: Thunkable[A]) = A.thunk(a)
}