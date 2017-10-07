package computerdatabase

import java.util.UUID

import scala.util.Random
import scala.language.postfixOps
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._


object TravelTest{



  val viewTravelValidated = exec(
    http("Views All Travels Validated")
      .get("/TravelAcceptationService/validate")
      .header("Content-Type", "application/json")
      .asJSON
      .check(status is 200)
  )

  val viewTravelRefused = exec(
    http("Views All Travels Refused")
      .get("/TravelAcceptationService/refuse")
      .header("Content-Type", "application/json")
      .asJSON
      .check(status is 200)
  )

  val viewTravelValidatedByUser = exec(
    http("Views All Travels Validated by User")
      .get("/TravelAcceptationService/validate/email/test")
      .header("Content-Type", "application/json")
      .asJSON
      .check(status is 200)
  )

  val viewTravelRefusedByUser = exec(
    http("Views All Travels Refused by User")
      .get("/TravelAcceptationService/refuse/email/test")
      .header("Content-Type", "application/json")
      .asJSON
      .check(status is 200)
  )

  val viewTravelValidatedByUid = exec(
    http("Views All Travels Validated by UID")
      .get("/TravelAcceptationService/validate/uid")
      .header("Content-Type", "application/json")
      .asJSON
      .check(status is 200)
  )

  val viewTravelRefusedByUid = exec(
    http("Views All Travels Refused by UID")
      .get("/TravelAcceptationService/refuse/uid")
      .header("Content-Type", "application/json")
      .asJSON
      .check(status is 200)
  )





}