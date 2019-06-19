import java.io.{BufferedOutputStream, FileOutputStream, OutputStream}

import play.api.libs.json.{JsPath, Json, Reads}
import play.api.libs.functional.syntax._
import scalaj.http.Http

import scala.io.Source


object S3_URL_to_Images extends App {



  case class Tongue(
                     _id: Option[String],
                     pic_web_uri: Option[String],
                     pic_bottom_web_uri:Option[String]
                   )


  implicit val tongueReads: Reads[Tongue] = (
    (JsPath \ "_id").readNullable[String] and
      (JsPath \ "pic_bottom_web_uri").readNullable[String] and
      (JsPath \ "pic_web_uri").readNullable[String]
    ) (Tongue.apply _)

  val localfile = Source.fromFile("2.json")

  val userString = localfile.getLines().toList

  for (i <- 0 until 16) {

    val test = userString(i)

    val tongueImage = Json.fromJson(Json.parse(userString(i)))(tongueReads)

    val aimUrl = tongueImage.get.pic_web_uri.get

    val _id = tongueImage.get._id.get

    println(aimUrl)

    val request = Http(aimUrl).asBytes.body

    Thread.sleep(4000)

    val out: OutputStream = new BufferedOutputStream(new FileOutputStream(s"target/bottom/${_id}.jpg"))

    out.write(request)

    out.flush()

    out.close()

    println(i)
  }
}
