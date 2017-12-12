name := "scala_learnings"

version := "0.1"

scalaVersion := "2.12.4"


//resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"


// Classic Actors, Typed Actors, IO Actor etc.
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.7"
libraryDependencies += "com.typesafe.akka" %% "akka-remote" % "2.5.7"
//libraryDependencies += "io.kamon" %% "kamon-akka-2.5" % "0.6.7"
//libraryDependencies += "io.kamon" %% "kamon-akka-remote" % "0.6.7"
//libraryDependencies += "io.kamon" %% "kamon-scala" % "0.6.7"
libraryDependencies += "io.kamon" %% "kamon-core" % "0.6.7"
// https://mvnrepository.com/artifact/io.kamon/kamon-statsd
libraryDependencies += "io.kamon" %% "kamon-statsd" % "0.6.7"
//libraryDependencies += "com.typesafe.akka" % "akka-typed" % "2.5.7"
// https://mvnrepository.com/artifact/org.aspectj/aspectjweaver
libraryDependencies += "org.aspectj" % "aspectjweaver" % "1.6.2"
