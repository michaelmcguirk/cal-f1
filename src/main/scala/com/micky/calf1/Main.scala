package com.micky.calf1

import cats.effect.IO
import com.micky.calf1.models.Circuit
import com.micky.calf1.modules.{Config, ErgastConfig, WiringModule}
//import com.micky.calf1.codecs.Encoders._
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.util.Await
import io.finch._
import io.finch.catsEffect._
import io.finch.circe._
import io.circe.generic.auto._
import com.micky.calf1.FinchConversions._
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App with WiringModule {

  case class Message(hello: String)

  def healthcheck: Endpoint[IO, String] = get(pathEmpty) {
    Ok("OK")
  }

/*  def helloWorld: Endpoint[IO, Message] = get("hello") {
    Ok(Message("World"))
  }

  def hello: Endpoint[IO, Message] = get("hello" :: path[String]) { s: String =>
    Ok(Message(s))
  }*/

  def circuits: Endpoint[IO, Circuit] = get("circuits" :: path[String]) { id: String =>
    val r = ergast.getCircuitM(id).asTwitter
    r.map(Ok)
    /*ergast.getCircuit(id) match {
      case Some(circuit) => Ok(List(circuit))
      case None => Output.empty(Status.NotFound)
    }*/
  }

  def service: Service[Request, Response] = Bootstrap
    .serve[Text.Plain](healthcheck)
    .serve[Application.Json](circuits)
    .toService

  Await.ready(Http.server.serve(":8081", service))
}