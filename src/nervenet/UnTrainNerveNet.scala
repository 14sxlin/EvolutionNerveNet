package nervenet

/**
  * Created by sparr on 2017/5/12.
  */
@SerialVersionUID(512)
case class UnTrainNerveNet[T](top: NerveTop[T])
  extends  NerveNet(top) with Serializable {

  private[this] var currentCase = 0 // 指代当前训练使用的是哪个用例

  /**
    * 总的周期
    */
  var totalRound = 100
  /**
    * 总的训练次数
    */
  var totalTrainTime = 0

  /**
    * 当前的迭代次数
    */
  var currentTrainTime = 0


  /**
    * 误差精确度
    */
  var accuracy = 0.001


  /**
    * 设置训练的周期
    * @param round 训练周期
    */
  def setTrainRound(round:Int): Unit = {
    if(trainCase==null || trainCase.length==0)
      throw new Exception("没有训练用例")
    totalRound = round
    totalTrainTime = totalRound * trainCase.length
  }
  /**
    * 计算误差和
    */
  def sumError():Double = {
    var sum = 0.0
    val last = realOutput.length-1
    for(j <- realOutput(last).indices)
    {
      sum += Math.pow(expOutput(currentCase)(j) - realOutput(last)(j),2)
    }
    println(s"time = $currentTrainTime  error = $sum")
    sum
  }

  /**
    * 判断训练是否应该结束
    * @return
    */
  private def hasFinish : Boolean = {
    if(currentTrainTime>0 && (currentTrainTime>=totalTrainTime || sumError()<=accuracy)) {//
      println("**************** round = " + currentTrainTime)
      return true
    }
    false
  }

  /**
    * 训练模型
    */
  def train():Unit = {
    if(totalTrainTime == 0)
      throw new Exception("没有设置训练用例")
    currentTrainTime = 0
    while(!hasFinish){
      clearOutput()
      for( i <- trainCase(currentCase).indices) //输入训练用例
        realOutput(0)(i) = trainCase(currentCase)(i)
      calOutput()
      calError(currentCase)
      updateWeight()
      updateXita()
      currentCase += 1
      currentCase %= trainCase.length
      currentTrainTime += 1
      //      a = 1.0/(round+1)
      //      printRealOutput()
      //      printErr()
      //      printWeight()
      //      printXita()
    }
  }

  def toTrainedNerveNet:TrainedNerveNet[T] = {
    val trainedNerveNet = new TrainedNerveNet[T](top)
    trainedNerveNet.expOutput = expOutput
    trainedNerveNet.resultClass = resultClass
    trainedNerveNet.err = err
    trainedNerveNet.realOutput = realOutput
    trainedNerveNet
  }

}
