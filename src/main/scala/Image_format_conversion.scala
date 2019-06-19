import java.awt.image.BufferedImage
import java.io.File

import javax.imageio.ImageIO

object Image_format_conversion extends App {

  def subDir(dir: File): Iterator[File] = {

    val dirs = dir.listFiles().filter(_.isDirectory())

    val files = dir.listFiles().filter(_.isFile())

    files.toIterator ++ dirs.toIterator.flatMap(subDir _)

  }


  def phototest(img: BufferedImage): BufferedImage = {

    val w = img.getWidth
    val h = img.getHeight

    val out = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)


    for (x <- 0 until w)
      for (y <- 0 until h)
        out.setRGB(x, y, img.getRGB(x, y) & 0xffffff)
    out
  }


  for (d <- subDir(new File("target/target11"))) {

    val dd = d.toString.split("/")(2)

    val aa = dd.replace(".", ",").split(",")(0)


    val photo1 = ImageIO.read(d)

    val photo2 = phototest(photo1)

    ImageIO.write(photo2, "jpg", new File(s"target/fullll/${aa}.jpg"))


  }
}
