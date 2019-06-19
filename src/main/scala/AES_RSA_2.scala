import javax.crypto.{Cipher, SecretKeyFactory}
import javax.crypto.spec.{IvParameterSpec, PBEKeySpec, SecretKeySpec}
import org.apache.commons.codec.binary.Base64
import play.api.libs.json.Json

object AES_RSA_2 extends App {

  val iv:String = "1234567812345678"
  val aesKey = "1234567812345678"
  val salt:String = "1234567812345678"

  // 取得 AES，模式用 CBC，使用 PKCS5Padding
  val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")


  // 開始做鑰匙，用PBKDF2WithHmacSHA1算法
  val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
  // 設定值
  val spec = new PBEKeySpec(aesKey.toCharArray, salt.getBytes("UTF-8"), 1, 128)
  // 做 AES 的 key
  val key = new SecretKeySpec(factory.generateSecret(spec).getEncoded, "AES")

  // 設定 iv spec
  val ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"))

  println(ivSpec.getIV.mkString(""))

  cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec)


  val json = Json.obj(

    "pic_tongue_id" -> "5add50552ab79c000176489f",
    "doctor_id" -> "jiajia",
    "doctor_name" -> "贾迦",
    "comment_content" ->"很好"

  )

  val oo = Json.stringify(json)

  val encrypted = cipher.doFinal(oo.getBytes("UTF-8"))

  //    println(s"encrypted = This is the text")

  val result = Base64.encodeBase64String(encrypted)

  println(s"encrypt result = ${result}")

  //初始化 cipher 設定
  cipher.init(Cipher.DECRYPT_MODE, key, ivSpec)

  // 解密
  val decrypted = cipher.doFinal(Base64.decodeBase64("7TfTpjyM9MHHO/hh9gDNwnvHOlfVTP5D9Y0ljAVn5QtktaFI+WLkf+9NpMlCJu7muWDgR8vUufcBP/CWTUI6X8EQl/l0l76a9uNI+mFcP6xoYbPog+QH0lOw5IaRqi//"))

  //   確認
  println(s"decrypt result = ${new String(decrypted, "UTF-8")}")

  println(new String(decrypted, "UTF-8"))



}
