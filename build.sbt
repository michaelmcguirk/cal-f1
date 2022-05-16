val finchVersion = "0.26.0"
//val finchVersion = "0.31.0"
//val circeVersion = "0.14.1"
val circeVersion = "0.10.1"
val scalatestVersion = "3.0.5"

lazy val root = (project in file("."))
  .settings(
    organization := "com.micky",
    name := "calf1",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.7",
    libraryDependencies ++= Seq(
      "com.github.finagle" %% "finchx-core"  % finchVersion,
      "com.github.finagle" %% "finchx-circe"  % finchVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "org.scalatest"      %% "scalatest"    % scalatestVersion % "test",
      "com.softwaremill.macwire" %% "macros" % "2.5.7" % "provided",
      "com.softwaremill.sttp.client3" %% "async-http-client-backend-future" % "3.5.2",
      "com.softwaremill.sttp.client3" %% "core" % "3.5.2",
      "com.github.pureconfig" %% "pureconfig" % "0.17.1",
      "com.github.cb372" %% "scalacache-guava" % "0.28.0",
    )
  )