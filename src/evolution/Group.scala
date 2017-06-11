package evolution

/**
  * Created by sparr on 2017/6/10.
  * @tparam T 表示个体编码的值
  */
abstract class Group[T](val codeLen:Int,val size:Int){

  var individuals :Array[T]  = _
  var breedPool : BreedPool[T] = _

  protected val rate = new Array[Double](size)

  /**
    * 随机初始化个体
    */
  def randomInitialize(): Unit

//  /**
//    * 个体适应环境,更新种群
//    * @param fun 适应环境
//    * @return
//    */
//  def adapt(fun:(Double*) => (Double)):Unit


  /**
    * 杂交和变异
    * @param crossRate 杂交的概率
    * @param variationRate 变异的概率
    */
  def mix(crossRate:Double,variationRate:Double):Unit

  /**
    * 获取最接近的整数值
    * @param value 输入的数
    * @return
    */
  def getCount(value:Double) :Int = {
    val i1 = (value+1).toInt
    val i2 = value.toInt
    if(Math.abs(i1-value)<Math.abs(i2-value))
      i1
    else i2
  }

}
