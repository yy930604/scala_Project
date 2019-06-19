import play.api.libs.json.{JsNull, Json}
import scalaj.http.Http

import scala.io.Source

object URL_Change_Base64 extends App {

  val localFile = Source.fromFile("tongue_tag.json")

  localFile.getLines.toList.map{ line =>

    val jsonValue = Json.parse(line)

    println(line)

    val uri = jsonValue.\("pic_web_uri").get.toString

    val request = Http(uri).asBytes.body

    val base64Imgae = java.util.Base64.getEncoder.encodeToString(request)

    val jsonObject = Json.obj(

      "delete" -> jsonValue.\("delete").get,

      jsonValue.\("pic_tag_time").get match {

        case JsNull =>{

          "pic_tag_time" -> JsNull

        }
        case _ => {

          "pic_tag_time" -> jsonValue.\("pic_tag_time").\("$numberLong").get.as[String].toLong

        }
      },

        "pic_user_id" ->  jsonValue.\("pic_user_name").get,
        "pic_user_name" -> jsonValue.\("pic_user_name").get,
        "pic_user_describe" -> jsonValue.\("pic_user_describe").get,
        "pic_doctor_name" -> jsonValue.\("pic_doctor_name").get,
        "pic_doctor_id" -> jsonValue.\("pic_doctor_id").get,
        "pic_upload_time" -> jsonValue.\("pic_upload_time").\("$numberLong").get.as[String].toLong,
        "pic_web_uri" -> jsonValue.\("pic_web_uri").get,
        "pic_base64" -> JsNull,
        "pic_tongue_status" -> jsonValue.\("pic_tongue_status").get,
        "pic_tongue_quality" -> jsonValue.\("pic_tongue_quality").get,
        "pic_tongue_affect_areas" -> jsonValue.\("pic_tongue_affect_areas").get,
        "pic_tongue_disease" -> jsonValue.\("pic_tongue_disease").get,
        "pic_tongue_disease_status" -> jsonValue.\("pic_tongue_disease_status").get,
        "pic_tongue_disease_proof" -> jsonValue.\("pic_tongue_disease_proof").get,
        "pic_tongue_describe" -> jsonValue.\("pic_tongue_describe").get,
        "pic_tongue_probably_prescription" -> jsonValue.\("pic_tongue_probably_prescription").get,
        "pic_other" -> jsonValue.\("pic_other").get

    )

    println(jsonObject)

  }
}
