package computerdatabase

import java.util.UUID
import scala.language.postfixOps

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._


class RegistrySimulation extends Simulation {

  val httpConf =
    http
      .baseURL("http://localhost:9080/tcs-service-doc/")
      .acceptHeader("application/json")
      .header("Content-Type", "application/json")

  val stressSample =
    scenario("Registering Flight")
        .repeat(2)
        {
          exec(session =>
            session.set("from", UUID.randomUUID().toString)
          )
            .exec(
              http("registering a flight")
                .post("flights")

                .body(StringBody(session => buildRetrieve(session)))
                .check(status.is(200))
            )

        }

  def buildRetrieve(session: Session): String = {
    val from = session("from").as[String]
    raw"""{
      "event": "RETRIEVE",
      "flight": {
        "from": "Nice",
        "to":   "Lyon",
        "dateFrom": "20/12/17",
        "duration": "4",
        "price" : "100",
        "directFlight" : "true",
      }
    }""""
  }




  setUp(stressSample.inject(rampUsers(20) over (10 seconds)).protocols(httpConf))
}