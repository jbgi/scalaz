package scalaz

trait Suspendable[A] {
  def suspend(a: =>A): A
}
