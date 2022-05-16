package com.micky.calf1.modules
import com.micky.calf1.services.DefaultErgastF1
import com.softwaremill.macwire._

trait WiringModule {
  lazy val config = Config.loadConfig()
  lazy val ergast = wire[DefaultErgastF1]
  println("called....")
}
