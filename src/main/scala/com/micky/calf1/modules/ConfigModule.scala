package com.micky.calf1.modules
import pureconfig._
import pureconfig.generic.auto._

final case class ErgastConfig(url: String)
final case class Config(ergast: ErgastConfig)

object Config {
  import pureconfig.generic.semiauto._

  implicit val configReader = deriveReader[Config]

  def loadConfig(): Config = {
    ConfigSource.default.loadOrThrow[Config]
  }
  def apply(): Config = {
    ConfigSource.default.loadOrThrow[Config]
  }
}
