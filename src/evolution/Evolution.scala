package evolution

/**
  *
  * @param codeLen 个体的编码长度
  * @param from 区间左
  * @param to 区间右
  * @param size 种群大小
  */
class Evolution(codeLen:Int,from:Double,to:Double,size:Int) {

  var group : Group01 = _

  /**
    * 适应函数
    */
  var adaptFun: (Double,Double) => Double = _

  /**
    * 初始化种群
    */
  def initGroup(): Unit ={
    group = new Group01(codeLen,size,from,to)
    group.randomInitialize()

  }

  /**
    * 设置函数的参数
    * @param j 函数参数的值
    */
  def setAdaptParam(j:Double): Unit = {
    adaptFun = (x1:Double, x2:Double) => {
      1.0/(3-Math.pow(Math.sin(x1*j),2)-Math.pow(Math.sin(x2*j),2))
    }

//    adaptFun = (x : Double) => { 1.0/ x }
  }

  /**
    * 生成繁殖池
    */
  def genBreedPool() : Unit = {
    group.adapt(adaptFun)
  }

  /**
    * 杂交和变异
    * @param crossRate 杂交的概率
    * @param variationRate 变异的概率
    */
  def mix(crossRate:Double,variationRate:Double): Unit = {
    group.mix(crossRate,variationRate)
  }
}

object Evolution{
  /**
    * 计算区间大小
    * @param accuracy 精度,将区间分成多少10的accuracy次方等分
    * @return
    */
  def calCodeLen(accuracy:Long,from:Double,to:Double): Int = {
    var count = 0
    while((1<<count)<(to-from)*Math.pow(10,accuracy))
      count += 1
    count
  }
}
