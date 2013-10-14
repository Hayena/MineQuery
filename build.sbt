name := "minequery"

version := "1.0.0-SNAPSHOT"

organization := "eu.trefan"

publishTo := {
  val repo = if (version.value endsWith "SNAPSHOT") "snapshot" else "release"
  Some("Trefan " + repo.capitalize + " Repository" at "http://maven.trefan.eu/artifactory/libs-" + repo + "-local")
}

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")  