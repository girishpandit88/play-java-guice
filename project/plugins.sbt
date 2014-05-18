// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers ++= Seq(
	 "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
	 "Maven Repository" at "http://repo1.maven.org/maven2/"
	 )

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.3")