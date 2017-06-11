import java.io.FileNotFoundException
import java.nio.charset.MalformedInputException

import evolution.Evolution
import org.kohsuke.args4j.{CmdLineException, CmdLineParser, Option}

import collection.JavaConversions._

/**
  * Created by sparr on 2017/5/18.
  */
object Main_Evolution extends App{

  @Option(name = "-from",usage="指定区间左",required = true)
  var from:Double = _
  @Option(name = "-to",usage="指定区间右",required = true)
  var to:Double = _
  @Option(name = "-size",usage="指定种群大小",required = true)
  var size:Int = _

  @Option(name ="-maxCount",usage = "指定最大迭代次数",required = false)
  var maxCount:Int = 1000

  @Option(name="-j" ,usage="指定适应函数的参数",required = false)
  var j:Double = 5

  @Option(name="-a" ,usage="指定精确度,10^n",required = false)
  var accuracy = 3

  @Option(name="-paramNum",usage="指定适应函数的参数个数")
  var paramNum = 2

  @Option(name = "-codeLen",usage="指定编码的长度",required = false)
  var codeLen:Int = _

  val myArgs = Array("-from","0","-to","6","-size","30")
  val parser = new CmdLineParser(this)
  try {
    parser.parseArgument(myArgs.toIterable)
    codeLen = Evolution.calCodeLen(accuracy,from,to)*paramNum
    println("codeLen = "+codeLen)
    val evolution = new Evolution(codeLen, from, to, size)
    evolution.initGroup()
    evolution.setAdaptParam(j)
    while(maxCount>0)
    {
      evolution.genBreedPool()
      evolution.mix(0.75,0.01)
      maxCount -= 1
    }

    val result = new Array[Double](size)
    var index = 0
    for(individual <- evolution.group.individuals){
      val (v1,v2) = individual.code2Value(codeLen/2,individual.code)
      println(s"v1 = $v1 v2 = $v2")
      val value = evolution.adaptFun(v1,v2)
      println(s" value = $value")
      result(index) = value
      index += 1
    }


    println(s"func: 3-sin($j*x1)^2-cos($j*x2)^2 (x1,x2 in" +
      s"[$from,$to]) ${result(0)}")



  }
  catch {
    case e:CmdLineException =>
      println(e.getMessage)
      parser.printUsage(System.err)
    case e:FileNotFoundException =>
      e.printStackTrace(System.out)
    case e:MalformedInputException =>
      println("数据模型的编码错误")
      e.printStackTrace(System.out)
  }
}
