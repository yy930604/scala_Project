import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec

import javax.crypto.Cipher
import org.apache.commons.codec.binary.Base64

object AES_RSA_1 extends App {
  // 做 RSA KEY 的方法
  //    def generateKeyPair: (PublicKey, PrivateKey) = {
  //      val gen = java.security.KeyPairGenerator.getInstance("RSA")
  //      gen.initialize(1024)
  //      val pair = gen.generateKeyPair
  //      (pair.getPublic, pair.getPrivate)
  //    }
  //
  //    // 用上面的方法，生成key
  //    val (pk, sk) = generateKeyPair
  //
  //    // 查看key的格式
  //    println(pk.getFormat) //X.509
  //    println(sk.getFormat) //PKCS#8
  //
  //    // 測試
  //    val cipher = Cipher.getInstance("RSA")
  //    cipher.init(Cipher.ENCRYPT_MODE, pk)
  //    val test = Base64.encodeBase64String(cipher.doFinal("1234567890".getBytes("UTF-8")))
  //
  //    // 將key pair用base64 String 存下來
  //    val pubString = "-----BEGIN PUBLIC KEY-----" +  Base64.encodeBase64String(pk.getEncoded) + "-----END PUBLIC KEY-----"
  //    val priString = "-----BEGIN PRIVATE KEY-----" +  Base64.encodeBase64String(sk.getEncoded) + "-----END PRIVATE KEY-----"
  //
  //    println(pubString)
  //    println(priString)



  //   base64的 private key
  val priString = """-----BEGIN PRIVATE KEY-----MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALZNlFla0LG1Z0nNS/3+gcxxICCAVuYWAddYuwPl0ohsA5/F13DL2TAuVFf8dpf4LgLqxeh9kw2WNgk+lKkpSQI2SXP0IcLA0zE+4UtfpS70qkNKXB37zNzggiNTeUmpJkGHTKKw4v6kcwE8YReFeZidXfflmBN2uLmyYFnAt3VTAgMBAAECgYA4/QF9+VS56NJUENhLmy7qQQbhAh2oKYMD1ZoIuuRYfyOW45rYiPDpDeKR6rsMAKopgUjCx9abbbSQqT6BrwVCI0mqndQuuqdkedXjvajH8NkWm/J1TvafIr04oVId+nEBknZ1vRvLL3Qfd+8IR4F9XtrOQw2tOidArnkO5ebAmQJBAOmaupauV+HsAbGQgV6DQA3qDUZDfdrBc1Hi5JD4Owi57MSF6n64fV5kwm3wC7ONs3Af/kKDRWIdGS1Ni0iQNP8CQQDHx8d6oYHaNzTvUKT5BCxijuJ4F9wtpCJG55QMgcpnziKPRLlTcrBqvT++LaqiTZnSu7sPaFKsv/qq3SH6k1utAkBvp0DfnFzs4ry8ffEXl2waOnA10TRqRmQ0E1pj4AF3XkZIeClogA2L/GTh3wX4wBWv1drVN6EGEBpkiNe1yxdJAkEAuA6T9qQzuTKhn1YiF5XDuq2/cjsxTA9q0P5sBLXTH53ncy0yvPHuHPjscC8shMmPzXnN1l0bxjI9JAwFURFQJQJBANnKkFZFEpwEfQQ3n3gF4PtX9FL3jZC4mD8OTnAVhUHWdAQ9nORN0JgXu4zSWIuo2A9gRrpzjc2FW0cmj5MqoyY=-----END PRIVATE KEY-----"""

  // base64的 public key
  //  val pubString = """-----BEGIN PUBLIC KEY----------END PUBLIC KEY-----"""

  // 前處理
  val privateKeyContent = priString.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "")
  //  val publicKeyContent = pubString.replaceAll("\\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "")

  println(privateKeyContent)
  //  println(publicKeyContent)

  // 用 KeyFactory 做key
  val kf = KeyFactory.getInstance("RSA")

  // private key 是 PKCS8 格式，用 PKCS8EncodedKeySpec 做
  val keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyContent))
  val privKey = kf.generatePrivate(keySpecPKCS8)


  println("="*100)
  println(privKey)
  println("="*100)
  //  println(pubKey)
  println("="*100)
  //  println(test)

  // 用 cipher 解密
  val cipher = Cipher.getInstance("RSA")
  cipher.init(Cipher.DECRYPT_MODE, privKey)
  //  val result = cipher.doFinal(Base64.decodeBase64(test), 0, 128)
  val result = cipher.doFinal(Base64.decodeBase64("EKajw5r4SlIJFsp8Zy2zYUDFy8UDoa0ltn4jOjttT9Fr+w15uJ…lBGwOYyPJgmmKIDzA4P6pAAG0EhE14JDesLe8tqzLxjgioF4="), 0, 128)

  println(new String(result,"utf-8"))

}
