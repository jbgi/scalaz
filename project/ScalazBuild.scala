import sbt._
import Keys._

import pl.project13.scala.sbt.JmhPlugin

object Scalaz extends Build {
  val testDeps = Seq("org.scalacheck" %% "scalacheck" % "1.13.2" % "test")

  def module(prjName: String) = Project(
    id = prjName,
    base = file(prjName)).settings(
    name := s"scalaz-$prjName",
    scalaVersion := "2.12.0-RC1",
    scalacOptions ++= Seq("-feature", "-deprecation", "-Xlint", "-language:higherKinds", "-target:jvm-1.8"),
    libraryDependencies ++= testDeps ++ Seq(
      compilerPlugin("org.spire-math" %% "kind-projector" % "0.9.0")
    )
  )

  lazy val root = Project(
    id = "root",
    base = file(".")
  ).settings(
    scalaVersion := "2.12.0-RC1"
  ).aggregate ( baze
              , meta
              , benchmarks )

  lazy val baze         = module("base")
    .dependsOn( meta )

  lazy val benchmarks   = module("benchmarks")
    .dependsOn( baze )
    .enablePlugins(JmhPlugin)
    .settings(
      libraryDependencies ++=
        Seq ( "org.scala-lang" % "scala-reflect" % scalaVersion.value
          , "org.scala-lang" % "scala-compiler" % scalaVersion.value % "provided"
          , "org.scalaz" %% "scalaz-core" % "7.3.0-M5")
    )

  lazy val meta         = module("meta")
    .settings(
      libraryDependencies ++=
        Seq ( "org.scala-lang" % "scala-reflect" % scalaVersion.value
          , "org.scala-lang" % "scala-compiler" % scalaVersion.value % "provided" )
    )
}