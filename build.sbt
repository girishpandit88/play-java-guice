name := "s3service"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "com.amazonaws" % "aws-java-sdk" % "1.7.1",
  "com.google.inject" % "guice" % "4.0-beta"
)     

play.Project.playJavaSettings
