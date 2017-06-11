package evolution

/**
  * Created by sparr on 2017/6/10.
  */
abstract class Individual[T] {

  var codeLen:Int = _
  var code : Array[T] = _

  /**
    * 获得个体适应值
    * @param fun 1个输入参数的函数
    */
  def getAdaptRate(fun:(Double) => Double) : Double

  /**
    * 获得个体适应值,一个编码从中间分成两个数
    * @param fun 两个输入参数的函数
    */
  def getAdaptRate(fun:(Double, Double) => Double): Double





}
