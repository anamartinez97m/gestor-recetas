name := """recipes-manager"""
organization := "com.recipes"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.6"
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

libraryDependencies += guice
libraryDependencies ++= Seq(
    "org.hibernate" % "hibernate-core" % "4.3.0.CR1",
    "org.hibernate" % "hibernate-entitymanager" % "4.3.0.CR1",
    "org.hibernate.javax.persistence" % "hibernate-jpa-2.1-api" % "1.0.0.Draft-16"
)

enablePlugins(PlayEbean)
libraryDependencies += evolutions
libraryDependencies += jdbc

libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.18"