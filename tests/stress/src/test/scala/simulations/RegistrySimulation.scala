package computerdatabase

import java.util.UUID
import scala.language.postfixOps

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._


class RegistrySimulation extends Simulation {

  val httpConf =
    http
      .baseURL("http://localhost:9080/tcs-service-doc/flights")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")

  val stressSample =
    scenario("Requiring all flights")
        .repeat(5)
        {
          exec()
            .exec(
              http("requiring all flights")
                .post("flights")
                .body(StringBody(session => buildRetrieve(session)))
                .check(status.is(200))
            )

        }

  def buildRetrieve(session: Session): String = {
    raw"""{
      "event": "RETRIEVE"
    }""""
  }




  setUp(stressSample.inject(rampUsers(20) over (10 seconds)).protocols(httpConf))
}