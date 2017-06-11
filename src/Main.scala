import java.io.{File, FileNotFoundException}

import config.Config
import evolution.EvolutionNerveNet
import org.kohsuke.args4j.{CmdLineException, CmdLineParser, Option}
import collection.JavaConversions._

/**
  * Created by sparr on 2017/6/10.
  */
object Main extends App {

  @Option(name = "-size", usage = "指定种群大小", required = true)
  var size: Int = _

  @Option(name = "-round", usage = "指定神经网络训练周期")
  var round: Int = 100

  @Option(name = "-maxCount", usage = "指定最大进化次数", required = false)
  var maxCount: Int = 10

  @Option(name = "-p", usage = "指定配置文件的路径", required = true)
  var configPath = ""

  @Option(name = "-cross",usage = "指定杂交概率")
  var crossRate = 0.75

  @Option(name ="-var",usage = "指定变异的概率")
  var varianceRate = 0.02


  var topPath = ""
  var trainPath = ""

  def init(): Unit = {
    val properties = Config.load(new File(configPath))
    topPath = properties.getProperty("topPath")
    trainPath = properties.getProperty("trainPath")
    println("configPath = " + configPath)
    println("topPath = " + topPath)
    println("trainPath = " + trainPath)
    println("load success...\n\n\n")
  }


  val parser = new CmdLineParser(this)
  try {
    parser.parseArgument(args.toIterable)
    init()
    val evolutionNerveNet = new EvolutionNerveNet(maxCount)
    evolutionNerveNet.begin(
      new File(topPath),
      new File(trainPath),
      size,
      round)
    evolutionNerveNet.setTrainRound(round)
    evolutionNerveNet.printWeights()
    while(!evolutionNerveNet.hasFinish)
    {
      evolutionNerveNet.adapt( (x:Double) => 1.0/x)
      evolutionNerveNet.mix(crossRate,varianceRate)
    }
    evolutionNerveNet.printWeights()
  } catch {
    case e:FileNotFoundException =>
      println("文件无法找到")
      e.printStackTrace()
    case _:CmdLineException =>
      parser.printUsage(System.err)
  }




}
