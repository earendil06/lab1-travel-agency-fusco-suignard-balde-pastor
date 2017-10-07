package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.language.postfixOps


object TravelTest{



  val viewTravelValidated = exec(
    http("Views All Travels Validated")
      .get("/TravelAcceptationService/validatedRequest")
      .header("Content-Type", "application/json")
      .asJSON
      .check(status is 200)
  )

  val viewTravelRefused = exec(
    http("Views All Travels Refused")
      .get("/TravelAcceptationService/refusedRequest")
      .header("Content-Type", "application/json")
      .asJSON
      .check(status is 200)
  )

  val viewTravelValidatedByUser = exec(
    http("Views All Travels Validated by User")
      .get("/TravelAcceptationService/validatedRequest/email/test")
      .header("Content-Type", "application/json")
      .asJSON
      .check(status is 200)
  )

  val viewTravelRefusedByUser = exec(
    http("Views All Travels Refused by User")
      .get("/TravelAcceptationService/refusedRequest/email/test")
      .header("Content-Type", "application/json")
      .asJSON
      .check(status is 200)
  )

  val viewTravelValidatedByUid = exec(
    http("Views All Travels Validated by UID")
      .get("/TravelAcceptationService/validatedRequest/uid")
      .header("Content-Type", "application/json")
      .asJSON
      .check(status is 200)
  )

  val viewTravelRefusedByUid = exec(
    http("Views All Travels Refused by UID")
      .get("/TravelAcceptationService/refusedRequest/uid")
      .header("Content-Type", "application/json")
      .asJSON
      .check(status is 200)
  )





}