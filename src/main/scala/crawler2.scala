import java.io.{File, PrintWriter}

import org.jsoup.Jsoup
import scalaj.http.Http
import collection.JavaConverters._
import scala.util.matching.Regex

object crawler2 extends App {

  val baseUrl = "http://data.weather.gov.hk/gts/time/conversion1_text_c.htm"

  val request = Http(baseUrl).charset("utf-8").timeout(5000,10000).asString.body

  val format = Jsoup.parse(request)

  val value = format.select("dl").select("p").asScala.toList.drop(2).toString()

  val valueString = Jsoup.parse(value)

  val patten = new Regex("\\d+")

  val aimUrl = valueString.select("a").eachAttr("href")

  val aimNum = (patten findAllIn aimUrl.toString()).toList


  for(i <- 0 until aimNum.length) {

    val aimUrll = s"http://data.weather.gov.hk/gts/time/calendar/text/T${aimNum(i)}c.txt"

    println(aimUrll)


    val request = Http(aimUrll).charset("big5").timeout(5000,15000).asString.body

    val writer = new PrintWriter(new File(s"target/year/${aimNum(i)}.txt"))

    writer.write(request)
    writer.close()

    println(i)

  }

}
