import java.io.File

import evolution.GroupNerveNet
import io.NerveNetIO
import nervenet.UnTrainNerveNet
import org.scalatest.FunSuite

/**
  * Created by sparr on 2017/6/10.
  */
class TestGroupNerveNet extends FunSuite {

  val groupSize = 10
  test("testInit"){
    val nerveNetIO = new NerveNetIO
    nerveNetIO.loadTop(new File("res/top_flower.input"))
    nerveNetIO.loadTrainCase(new File("res/train_flower.txt"))
    val groupNerveNet = new GroupNerveNet(groupSize,
      nerveNetIO.currentNerveNet.asInstanceOf[UnTrainNerveNet[String]],
      100)
    groupNerveNet.randomInitialize()
    for(temp <- groupNerveNet.individuals) {
//      assert(temp.code.length==temp.codeLen&&temp.codeLen==4)
      for (v <- temp.code)
        println("value = "+v)
    }
    assert(groupNerveNet.individuals.length==groupSize)
    groupNerveNet.adapt( (x:Double) => 1.0/x)

    groupNerveNet.mix(0.75,0.02)
  }




}
