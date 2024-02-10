ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.18"

lazy val root = (project in file("."))
  .settings(
    name := "scala-play-silhouette-compiletimedi",
    libraryDependencies ++= Seq(
      "com.softwaremill.macwire" %% "macros" % "2.5.9" % Provided,
      "com.softwaremill.macwire" %% "macrosakka" % "2.5.9" % Provided,

      "com.mohiva" %% "play-silhouette" % "5.0.7",
      "com.mohiva" %% "play-silhouette-password-bcrypt" % "5.0.7",
      "com.mohiva" %% "play-silhouette-crypto-jca" % "5.0.7",
      "com.mohiva" %% "play-silhouette-persistence" % "5.0.7",
      "com.mohiva" %% "play-silhouette-testkit" % "5.0.7" % "test",
    ),
    resolvers += "Atlassian Releases" at "https://maven.atlassian.com/public/"
  ).enablePlugins(PlayScala)
