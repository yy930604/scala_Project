import java.io.{ByteArrayInputStream, File, PrintWriter}
import java.nio.file.Paths
import java.time.{ZoneOffset, ZonedDateTime}
import com.amazonaws.auth.{AWSCredentials, AWSStaticCredentialsProvider}
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model._
import org.mongodb.scala.bson.ObjectId
import play.api.libs.json.{JsNull, Json}
import sun.misc.BASE64Decoder

object image_upload_S3 extends App {

  private val s3Client = AmazonS3ClientBuilder
    .standard()
    .withCredentials(
      new AWSStaticCredentialsProvider(
        new AWSCredentials {
          override def getAWSAccessKeyId: String = "**********************"
          override def getAWSSecretKey: String = "****************************"
        }
      )
    ).withRegion("cn-north-1").build()

  private val base64Decoder = new BASE64Decoder()


  def subDir(dir: File): Iterator[File] = {

    val dirs = dir.listFiles().filter(_.isDirectory())

    val files = dir.listFiles().filter(_.isFile())

    files.toIterator ++ dirs.toIterator.flatMap(subDir _)

  }

  for (d <- subDir(new File("target/base64/yangyang1"))) {

    val pp = d.getName.replace(".", ",").split(",")(0)
    println(pp)
    val btteArray = java.nio.file.Files.readAllBytes(Paths.get(d.getPath))
    Thread.sleep(2000)
    val tongueUploadTime = ZonedDateTime.now(ZoneOffset.UTC).toEpochSecond
    val tongueBottomImageS3prefix = s"${tongueUploadTime.toString}.jpg"
    val tongueBottomImageUri = s"http://test.herbmagic.cn/$tongueBottomImageS3prefix"
    val objectMetadata1 = new ObjectMetadata()
    objectMetadata1.setContentType("image/jpeg")
    objectMetadata1.setContentLength(btteArray.length)
    val acl = new AccessControlList()
    acl.grantPermission(GroupGrantee.AllUsers, Permission.Read)

    s3Client.putObject(
      new PutObjectRequest(
        "test.herbmagic.cn",
        tongueBottomImageS3prefix,
        new ByteArrayInputStream(btteArray),
        objectMetadata1
      ).withAccessControlList(
        acl
      )
    )
    val tongueId = new ObjectId().toString
    println(tongueBottomImageUri)
    val JsObject = Json.obj(
      "pic_id" -> tongueId,
      "img_url" -> tongueBottomImageUri,
      "point_1" -> JsNull,
      "point_2" -> JsNull,
      "tag_time" -> JsNull,
      "tongue_location" -> JsNull,
      "create_time" -> JsNull
    )
    val writer = new PrintWriter(new File(s"target/base64/yangyang1/${pp}.json"))
    val aa = Json.stringify(JsObject)
    writer.write(aa + "\n")
    writer.close()
  }
}
