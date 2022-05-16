package com.micky.calf1.services

import com.micky.calf1.models.ergast.ErgastGetCircuitResponse
import com.micky.calf1.models.{Circuit, RaceEvent}
import com.micky.calf1.modules.Config
import sttp.client3._
import sttp.client3.asynchttpclient.future.AsyncHttpClientFutureBackend
import io.circe._
import io.circe.parser._
import com.micky.calf1.codecs.Decoders._
import scalacache._
import scalacache.memoization._
import scalacache.modes.scalaFuture._
import scalacache.guava._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait ErgastF1 {
  def getRaceSchedule(year: Int): List[RaceEvent]
  def getCircuit(id: String): Future[Circuit]
}

class DefaultErgastF1(config: Config) extends ErgastF1 {
  val backend = AsyncHttpClientFutureBackend()
  implicit val guavaCache: Cache[Circuit] = GuavaCache[Circuit]

  def getRaceSchedule(year: Int): List[RaceEvent] = ???

  def getCircuitM(id: String): Future[Circuit] = memoizeF(Some(10.minutes)) {
    getCircuit(id);
  }

  def getCircuit(id: String): Future[Circuit] = {
    println("calling getCircuit")
    println(config.ergast.url);
    val response = basicRequest
      .get(uri"${config.ergast.url}/circuits/$id.json").send(backend)
    response.map{ r =>
      r.body match {
        case Left(error) =>
          println(error)
          throw new Exception
        case Right(resp) =>
          decode[ErgastGetCircuitResponse](resp) match {
            case Left(error) =>
              println(s"Failed parsing: $error")
              throw new Exception
            case Right(getCircuitResponse) => getCircuitResponse.circuit.get //ToDo: bad juju, fix this.
          }
      }
    }

  }

/*  def getCircuit2(id: String): Future[Circuit] = {
    println("config")
    println(config.ergast.url);
    val response = basicRequest
      .get(uri"${config.ergast.url}/circuits/$id.json").response(asJson[ErgastGetCircuitResponse]).send(backend)
    response.map{ r =>
      println(r.body)
      r.body match {
        case Left(error) => throw new Exception
        case Right(circuitResponse) =>
          circuitResponse.circuit match {
            case Some(circuit) => circuit;
            case None => throw new Exception;
          }
      }

    }

  }*/

}
