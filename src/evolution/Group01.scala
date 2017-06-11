package evolution

import scala.collection.mutable.ArrayBuffer

/**
  * Created by sparr on 2017/6/11.
  * 基因编码为01的种群
  */
class Group01(codeLen:Int,
              size:Int,
              val from:Double,
              val to:Double
              ) extends Group[Individual01](codeLen,size){

  individuals = new Array[Individual01](size)


  /**
    * 随机创建编码是01个体
    */
  def randomInitialize(): Unit = {
    for(i <- individuals.indices){
      individuals(i) =
        IndividualFactory.getRandom01Individual(codeLen,from,to)
    }
  }

  /**
    * 计算适应值把并且生成一个繁殖池
    * @param fun 双变量的适应函数
    * @return
    */
  def adapt(fun:(Double, Double) => Double): Unit = {
    for( i <- individuals.indices){
        rate(i) = individuals(i).getAdaptRate(fun)
    }
    val sumRate = rate.sum
    for( i <- rate.indices) {
      rate(i) /= sumRate
      println("rate = "+rate(i))
    }

    var entices = new ArrayBuffer[Individual01]()
    for( i <- rate.indices)
      for( j <- 0 until getCount(rate(i) * size)){
        entices += new Individual01(individuals(i))
      }
    println("next gen begin size = "+entices.length)
    breedPool = new BreedPool01(entices.toArray)
//    breedPool.printEntities()
  }


  /**
    * 杂交和变异
    * @param crossRate 杂交的概率
    * @param variationRate 变异的概率
    */
  def mix(crossRate:Double,variationRate:Double):Unit = {
    breedPool.mix(crossRate,variationRate)
    individuals = breedPool.getBestGroup(size)
  }

}
