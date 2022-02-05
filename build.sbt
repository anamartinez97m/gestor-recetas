name := """play-java-hello-world-tutorial"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.6"

libraryDependencies += guice

enablePlugins(PlayEbean)
libraryDependencies += evolutions
libraryDependencies += jdbc
// si falla algo, 09-modelo

libraryDependencies += "com.h2database" % "h2" % "1.4.200"