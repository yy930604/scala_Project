import java.io.File

import scalaj.http.Http

import scala.io.Source

object Post_to_DB extends App {

  def subDir(dir: File): Iterator[File] = {

    val dirs = dir.listFiles().filter(_.isDirectory())

    val files = dir.listFiles().filter(_.isFile())

    files.toIterator ++ dirs.toIterator.flatMap(subDir _)

  }

  for (d <- subDir(new File("target/base64/yangyang1"))) {

    println(d)

    val localfile = Source.fromFile(d)

    val userString = localfile.getLines().toList

    for (i <- 0 until userString.size) {

      val test = userString(i)

      println(test)

      Thread.sleep(2000)

      val requset = Http("http://localhost:9000/v1/indexpost").timeout(5000, 15000).postData(test).header("content-type", "application/json").asString.code
      println(requset)

    }
  }



}
