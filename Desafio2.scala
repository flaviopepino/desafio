/*
 * Copyright 2011-2020 GatlingCorp (https://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class Desafio2 extends Simulation {

  val httpProtocol = http
    .baseUrl("http://5d9cc58566d00400145c9ed4.mockapi.io/shopping_cart") // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val scn = scenario("Desafio 2")
	.forever {
		exec(
		  http("01 - Consulta API")
			.get("/")
			.check(
				substring("""{"shopping_cart":1,"sku":["demo_2","demo_1","demo_7"],"color":["Black","Orange","Yellow"],"size":["S","S","S"],"price":["27.00","16.51","16.40"],"total_shipping":"2.00"}"""))
		)
    }


  setUp(scn.inject(rampUsers(5) during (10 seconds)).protocols(httpProtocol)).maxDuration(30 seconds)
}
