import java.io.File

import io.NerveNetIO
import nervenet.NerveNet

/**
  * Created by sparr on 2017/6/10.
  */
class System {

  var nerveNet:NerveNet[String] = _

  val nerveNetIO = new NerveNetIO()

  def begin(topFile:File) = {
    nerveNetIO.loadTrainedNerveNet(topFile)
    nerveNet = nerveNetIO.currentNerveNet
  }

  def adapt(fun:(Double*) => Double) = {

  }


}
