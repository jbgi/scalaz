package scalaz
package std

import java.lang.Float.floatToRawIntBits
import core.EqAnyVal
import utils._

trait FloatInstances {
  implicit val floatDebug: Debug[Float] = toStringDebug[Float]
  implicit val floatEq: Eq[Float] = instanceOf(
    ((a, b) => floatToRawIntBits(a) == floatToRawIntBits(b)): EqAnyVal[Float]
  )
}
