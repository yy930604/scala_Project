import java.io.{File, PrintWriter}

import play.api.libs.json.Json
import scalaj.http.Http

object crawler1 extends App {

  val url = "http://210.73.61.178/DiagAssist/services/disease/get.json?_dc=1514166100568&q=头痛"
  val request = Http(url).charset("utf-8").timeout(5000, 150000).asString.body
  //取得数据 得到其body标签的关键字
  val writer = new PrintWriter(new File(s"target/json3/头痛.json"))
  writer.write(request)
  writer.close()


  val responseStringJson = Json.parse(request)
  //把得到到的request String类型改成json类型
  val symptomNamesList = responseStringJson.\\("symptomNames")
  //play json的固定套件 提取 特定标签内的所有症状名
  val symptomForYield = for (x <- 0 until symptomNamesList.length) yield {
    symptomNamesList(x).as[Set[String]]
  }
  //宣告一个不可变量 使x在一个区间内循环 并用yield方法将循环的每一次结果保存并使其形态为Set
  val symptomSet = symptomNamesList.map { x => x.as[Set[String]] }
  //宣告一个不可变量symptomSet 使用map方法 使symptomNamesList成为一个整体的Set
  var results = symptomSet.foldLeft(Set[String]())((a, b) => a.union(b))
  println(results)


  val baseUrl = "http://210.73.61.178/DiagAssist/services/disease/get.json?_dc=1514166100568&q="
  //先定义基础URL
  for (x <- 0 until results.size) {
    println("***" * 10)
    println(results.size)
    val queryString = results.toList(x)
    println(queryString)
    //将results转换成list类型


    val requestAgain = Http(baseUrl + queryString).charset("utf-8").timeout(5000, 150000).asString.body


    val symptomNamesListAgain = Json.parse(requestAgain).\\("symptomNames")

    //找到所有的症状名称
    val symptomSetAgain = symptomNamesListAgain.map { x => x.as[Set[String]] }


    //用map方法将其整合成一个Set
    val resultsAgain = symptomSetAgain.foldLeft(Set[String]())((a, b) => a.union(b))

    println(resultsAgain)

    //用foldLeft方法使其中的元素独一无二

    results = results.union(resultsAgain)

    //之前宣告过results是可变变量 将得到症状名称与之前的result去重并重新定义给results

    results.map { x =>

      println("===" * 10)

      println(x)

      val requestThird = Http(baseUrl + x).charset("utf-8").timeout(5000, 150000).asString.body

      val writer = new PrintWriter(new File(s"target/json3/$x.json"))

      writer.write(requestThird)

      writer.close()
    }
  }
}
