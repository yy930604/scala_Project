name := "clawer"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.7"

libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.3.0"
libraryDependencies +="org.scala-lang.modules" % "scala-xml_2.12" % "1.0.5"
libraryDependencies +="org.jsoup" % "jsoup" % "1.11.2"
libraryDependencies +="org.mongodb.scala" %% "mongo-scala-driver" % "2.2.0"

libraryDependencies += "commons-codec" % "commons-codec" % "1.4"

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.2.0"

libraryDependencies ++= Seq(
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.2.0",
  "com.amazonaws" % "aws-java-sdk-s3" % "1.11.258",
  "ai.x" %% "play-json-extensions" % "0.10.0",

)

