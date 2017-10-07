package computerdatabase

import java.util.UUID
import scala.language.postfixOps

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._


class TravelSimulation extends Simulation {


  val httpConf =
    http
      .baseURL("http://localhost:9070/service-travel-manager")

  val scenarioName = "List Travel"

  val scn = scenario(scenarioName)

    .exec(
      TravelTest.viewTravelValidated,
      TravelTest.viewTravelRefused,
      TravelTest.viewTravelValidatedByUser,
      TravelTest.viewTravelRefusedByUser,
      //TravelTest.viewTravelValidatedByUid,
      //TravelTest.viewTravelRefusedByUid


    )

  // set up the scenario and threads (users) count:
  setUp(scn.inject(rampUsers(20) over (10 seconds)))
    .protocols(httpConf)
}