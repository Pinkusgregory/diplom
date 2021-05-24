name := "diplom"
 
version := "1.0" 
      
lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.squeryl" %% "squeryl" % "0.9.5-7",
  "org.postgresql" % "postgresql" % "9.3-1102-jdbc4",
  "org.scala-lang" % "scala-library" % "2.11.11",
  "net.codingwell" %% "scala-guice" % "4.0.0",
  "org.ehcache" % "ehcache" % "3.6.1",
  "org.scalatest" % "scalatest_2.11" % "3.0.1" % "test",
  "com.google.inject" % "guice" % "3+",
  "com.typesafe.akka" %% "akka-stream" % "2.5.14",
  "net.liftweb" %% "lift-json-ext" % "2.6.2"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      