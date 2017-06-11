package evolution

import nervenet.UnTrainNerveNet

/**
  * Created by sparr on 2017/6/10.
  */
object IndividualFactory {

  /**
    * 随机生成一个01编码的个体
    * @param codeLen 编码的程度
    * @param from 十进制的取值范围左
    * @param to 十进制的取值范围右
    * @return
    */
  def getRandom01Individual(codeLen:Int,
                      from:Double,
                      to:Double): Individual01 = {
    new Individual01(codeLen,from,to)
  }


}
