package com.micky.calf1.models.ergast

import com.micky.calf1.models.Circuit

/*
ToDo: look into custom decoding in Circe so I don't have to rep all
 these unnecessary Ergast fields in the service models.
*/
final case class ErgastGetCircuitResponse(
  series: String,
  limit: Long,
  offset: Long,
  total: Long,
  circuit: Option[Circuit]
)
final case class ErgastCircuitTable(circuitId: String, Circuits: List[Circuit])
