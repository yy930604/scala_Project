import java.io.{File, PrintWriter}

import org.jsoup.Jsoup
import scalaj.http.Http

import scala.io.Source

object crawler3 extends App {

  val url = "https://www.xiangha.com/jiankang/"

  val request = Http(url).charset("utf-8").timeout(5000, 10000).asString.body

  val format = Jsoup.parse(request)

  val aimString = format.select("div.rec_classify_cell.clearfix").select("ul.clearfix").text().split(" ")

  val valueString = format.select("div.rec_classify_cell.clearfix").select("ul.clearfix").select("li").select("a").eachAttr("href").toString.split(",")



  for (i <- 0 until aimString.size) {

    val localfile = Source.fromFile("target/test.txt")

    val userString = localfile.getLines().toList


    val request2 = Http(userString(i)).charset("utf-8").timeout(5000, 10000).asString.body

    val format2 = Jsoup.parse(request2)

    val aimString2 = format2.select("div.hea_main")


    val writer = new PrintWriter(new File(s"ta/rget/yy1/${aimString(i)}.json"))

    writer.write(aimString2+"\n")
    writer.close()


  }

}
