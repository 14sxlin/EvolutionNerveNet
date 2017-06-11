package evolution

import java.io.File

import io.NerveNetIO
import nervenet.UnTrainNerveNet

/**
  * Created by sparr on 2017/6/11.
  */
class EvolutionNerveNet(maxEvolutionCount:Int) {

  private[this] var count = 0

  var groupNerveNet:GroupNerveNet = _

  /**
    * 设置训练周期
    * @param round
    */
  def setTrainRound(round:Int) :Unit = {
    groupNerveNet.nerveNet.setTrainRound(round)
  }

  /**
    * 是否结束
    * @return
    */
  def hasFinish : Boolean = {
    count >= maxEvolutionCount
  }


  /**
    * 开始进化神经网络的计算
    * @param topFile 网络的拓扑结构
    * @param trainFile 训练网络的用例
    * @param groupSize 种群的大小
    * @param trainTime 网络训练的最大迭代次数
    */
  def begin(topFile:File, trainFile:File, groupSize:Int, trainTime:Int=1000): Unit = {
    val nerveNetIO = new NerveNetIO
    nerveNetIO.loadTop(topFile)
    nerveNetIO.loadTrainCase(trainFile)
    groupNerveNet = new GroupNerveNet(groupSize,
      nerveNetIO.currentNerveNet.asInstanceOf[UnTrainNerveNet[String]],
      100)
    groupNerveNet.randomInitialize()
  }

  /**
    * 适应环境
    * @param fun 适应函数
    */
  def adapt(fun: Double => Double): Unit = {
    groupNerveNet.adapt(fun)
  }

  /**
    * 杂交变异
    * @param crossRate 杂交的概率
    * @param variationRate 变异的概率
    */
  def mix(crossRate: Double, variationRate: Double): Unit = {
    groupNerveNet.mix(crossRate,variationRate)
    count += 1
  }

  /**
    * 输出种群中的各个权重
    */
  def printWeights(): Unit ={
    groupNerveNet.printWeights()
  }

}
