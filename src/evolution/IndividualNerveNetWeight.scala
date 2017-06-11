package evolution

import nervenet.UnTrainNerveNet

/**
  * Created by sparr on 2017/6/10.
  * 神经网络权重的个体
  */
class IndividualNerveNetWeight(var nerveNet:UnTrainNerveNet[String])
  extends Individual[Double]{

  /**
    * 神经网络的误差平方和
    */
  var adapt : Double = _

  def this(nerveNet:UnTrainNerveNet[String],from:Double,to:Double) = {
    this(nerveNet)
    this.codeLen = nerveNet.top.weight.size
    code = new Array[Double](codeLen)
    for( i <- code.indices){
      code(i) = Math.random()*(to-from)-to
    }
  }

  /**
    * 复制一个相同的个体
    * @param individual 个体
    */
  def this(individual: IndividualNerveNetWeight){
    this(individual.nerveNet)
    this.codeLen = individual.codeLen
    this.code = new Array[Double](codeLen)
    for( i <- code.indices){
      code(i) = individual.code(i)
    }
  }


  /**
    * 获得个体适应值
    *
    * @param fun 1个输入参数的函数
    */
  override def getAdaptRate(fun: (Double) => Double): Double = {
    if(nerveNet == null)
      throw new Exception("没有神经网络")
    initWeight()
    nerveNet.train()
    var errors = 0.0
    for(line <- nerveNet.err)
      for( v <- line)
        errors += (v*v)
//    nerveNet.printWeight()
    adapt = fun(errors)
    println(s"adapt = $adapt")
    adapt
  }

  /**
    * 获得个体适应值,一个编码从中间分成两个数
    *
    * @param fun 两个输入参数的函数
    */
  override def getAdaptRate(fun: (Double, Double) => Double): Double = ???

  /**
    * 将个体转换为网络拓扑的权重
    */
  def initWeight(): Unit = {
    val tempWeight = nerveNet.top.weight
    var i = 0
    for( key <- tempWeight.keys){
      tempWeight(key) = code(i)
      i += 1
    }
  }

  def printWeight(): Unit = {
    val tempWeight = nerveNet.top.weight
    var i = 0
    for( key <- tempWeight.keys){
      println(s"${key._1} ${key._2} ${code(i)}")
      i += 1
    }
  }
}
