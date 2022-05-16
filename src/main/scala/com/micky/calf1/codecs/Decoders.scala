package com.micky.calf1.codecs
import com.micky.calf1.models.{Circuit, Location}
import com.micky.calf1.models.ergast.ErgastGetCircuitResponse
import io.circe.{Decoder, HCursor}
import io.circe.generic.semiauto._

object Decoders {
  implicit val locationDecoder: Decoder[Location] = deriveDecoder[Location]

  implicit val decodeGetCircuitResponse: Decoder[ErgastGetCircuitResponse] = (c: HCursor) => for {
    limit <- c.downField("MRData").downField("limit").as[Long]
    offset <- c.downField("MRData").downField("offset").as[Long]
    total <- c.downField("MRData").downField("total").as[Long]
    series <- c.downField("MRData").downField("series").as[String]
    circuit <- c.downField("MRData").downField("CircuitTable").as[Option[Circuit]]

  } yield {
    ErgastGetCircuitResponse(series, limit, offset, total, circuit)
  }

  implicit val decodeCircuit: Decoder[Circuit] = new Decoder[Circuit] {
    final def apply(c: HCursor): Decoder.Result[Circuit] =
      for {
        id <- c.downField("Circuits").downArray.downField("circuitId").as[String]
        url <- c.downField("Circuits").downArray.downField("url").as[String]
        name <- c.downField("Circuits").downArray.downField("circuitName").as[String]
        location <- c.downField("Circuits").downArray.downField("Location").as[Location]
      } yield {
        new Circuit(id, url, name,location)
      }
  }

}
