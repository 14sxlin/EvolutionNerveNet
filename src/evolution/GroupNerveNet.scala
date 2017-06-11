package evolution

import nervenet.{NerveNet, UnTrainNerveNet}

import scala.collection.mutable.ArrayBuffer

/**
  * 一个神经网络权重的种群
  * @param size 种群大小
  * @param nerveNet 对应的神经网络
  * @param trainTime 单次训练的次数
  */
class GroupNerveNet(size:Int,
                    val nerveNet : UnTrainNerveNet[String],
                    val trainTime:Int = 10000)
  extends Group[IndividualNerveNetWeight](nerveNet.top.weight.size,size){

  individuals = new Array[IndividualNerveNetWeight](size)

  /**
    * 随机初始化个体
    */
  override def randomInitialize(): Unit = {
    nerveNet.totalTrainTime = trainTime
    for( i <- individuals.indices){
      individuals(i) = new IndividualNerveNetWeight(nerveNet,-1.0,1.0)
    }
  }


  def adapt(fun:Double => Double) = {
    for( i <- individuals.indices){
      rate(i) = individuals(i).getAdaptRate(fun)
    }
    val sumRate = rate.sum
    for( i <- rate.indices) {
      rate(i) /= sumRate
      println("rate = "+rate(i))
    }

    var entices = new ArrayBuffer[IndividualNerveNetWeight]()
    for( i <- rate.indices)
      for( j <- 0 until getCount(rate(i) * size)){
        entices += new IndividualNerveNetWeight(individuals(i))
      }
    println("next gen begin size = "+entices.length)
    breedPool = new BreedPoolNerveNet(entices.toArray)
  }

  /**
    * 杂交和变异
    *
    * @param crossRate     杂交的概率
    * @param variationRate 变异的概率
    */
  override def mix(crossRate: Double, variationRate: Double): Unit = {
    breedPool.mix(crossRate,variationRate)
    individuals = breedPool.getBestGroup(size)
  }

  /**
    * 打印所有个体的权重
    */
  def printWeights(): Unit = {
    for( i <- individuals){
      println("---------top weight-------------")
      i.printWeight
    }
    println(";")
  }
}
