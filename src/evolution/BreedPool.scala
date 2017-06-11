package evolution

/**
  * Created by sparr on 2017/6/10.
  */
abstract class BreedPool[T](individuals: Array[T]) {

  def mix(crossRate: Double, variationRate: Double): Unit

  def cross(rate: Double): Unit

  def variation(rate: Double): Unit

  def getBestGroup(size: Int): Array[T]
}
