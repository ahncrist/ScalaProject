name := "SillyTalker"

version := "0.1"

scalaVersion := "2.13.4"

val http4sVersion = "0.21.12"

libraryDependencies ++= Seq(
  "io.chrisdavenport" %% "log4cats-slf4j"      % "1.1.1",
  "org.http4s"        %% "http4s-dsl"          % http4sVersion,
  "org.http4s"        %% "http4s-blaze-server" % http4sVersion,
  "org.http4s"        %% "http4s-blaze-client" % http4sVersion,
  "org.slf4j"         % "slf4j-simple"         % "1.7.30"
)
