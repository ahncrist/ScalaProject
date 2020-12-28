name := "SillyTalker"

version := "0.1"

scalaVersion := "2.13.4"

val http4sVersion = "0.21.12"
val circeVersion  = "0.12.3"

libraryDependencies ++= Seq(
  "io.chrisdavenport"     %% "log4cats-slf4j"      % "1.1.1",
  "org.http4s"            %% "http4s-dsl"          % http4sVersion,
  "org.http4s"            %% "http4s-blaze-server" % http4sVersion,
  "org.http4s"            %% "http4s-blaze-client" % http4sVersion,
  "org.http4s"            %% "http4s-circe"        % http4sVersion,
  "ch.qos.logback"        % "logback-classic"      % "1.2.3",
  "io.circe"              %% "circe-core"          % circeVersion,
  "io.circe"              %% "circe-generic"       % circeVersion,
  "io.circe"              %% "circe-parser"        % circeVersion,
  "com.github.pureconfig" %% "pureconfig"          % "0.14.0",
  "com.github.pureconfig" %% "pureconfig-http4s"   % "0.14.0",
  "co.fs2"                %% "fs2-core"            % "2.4.6",
  "org.tpolecat"          %% "doobie-core"         % "0.9.0",
  "org.tpolecat"          %% "doobie-hikari"       % "0.9.0",
  "org.tpolecat"          %% "doobie-postgres"     % "0.9.0"
)
