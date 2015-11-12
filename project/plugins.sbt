resolvers ++= Seq(
  Classpaths.typesafeReleases,
  Classpaths.sbtPluginReleases,
  "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/",
  Resolver.jcenterRepo
)
resolvers += Resolver.url(
  "tpolecat-sbt-plugin-releases",
    url("http://dl.bintray.com/content/tpolecat/sbt-plugin-releases"))(
        Resolver.ivyStylePatterns)

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.1.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8.3")
addSbtPlugin("com.typesafe.sbt" % "sbt-ghpages" % "0.5.3")
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.13.0")
addSbtPlugin("com.eed3si9n" % "sbt-unidoc" % "0.3.1")
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.6.0")
addSbtPlugin("org.brianmckenna" % "sbt-wartremover" % "0.13")
addSbtPlugin("pl.project13.scala" % "sbt-jmh" % "0.1.15")
addSbtPlugin("me.lessis" % "bintray-sbt" % "0.3.0")
addSbtPlugin("org.tpolecat" % "tut-plugin" % "0.4.0")
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.0")
