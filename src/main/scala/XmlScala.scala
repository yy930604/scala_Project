import org.jsoup.Jsoup
import scalaj.http.Http

object XmlScala extends App {

  val request = Http("https://www.smzdm.com/sitemap/smzdm_youhui.xml").timeout(5000, 15000).asString.body

  val Xmlstring = xml.XML.loadString(request)


  val urls = (Xmlstring.\\("loc")).map(_.text).toList.take(10)

  for (i <- 1 until urls.length ){

    val request = Http(urls(i)).asString.body

    val tag =  Jsoup.parse(request)

    val  oo = tag.select("title").text

    val  pp = oo.split("_")(0)

    println(pp)

  }

}
