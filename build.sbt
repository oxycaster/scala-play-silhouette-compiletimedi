ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.18"

lazy val root = (project in file("."))
  .settings(
    name := "scala-play-silhouette-compiletimedi",
    libraryDependencies ++= Seq(
      "com.softwaremill.macwire" %% "macros" % "2.5.9" % Provided,
      "com.softwaremill.macwire" %% "macrosakka" % "2.5.9" % Provided,
    )
  ).enablePlugins(PlayScala)
