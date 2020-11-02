
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class Desafio extends Simulation {

	val httpProtocol = http
		.baseUrl("http://automationpractice.com")
		.silentResources
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("pt-BR,pt;q=0.8,en-US;q=0.5,en;q=0.3")
		.doNotTrackHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0")

	val headers_0 = Map(
		"Cache-Control" -> "max-age=0",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map("Upgrade-Insecure-Requests" -> "1")

	val headers_3 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"Content-Type" -> "application/x-www-form-urlencoded; charset=UTF-8",
		"Origin" -> "http://automationpractice.com",
		"X-Requested-With" -> "XMLHttpRequest")

	val headers_8 = Map(
		"Origin" -> "http://automationpractice.com",
		"Upgrade-Insecure-Requests" -> "1")
		
	val emails = csv("emails.csv")



	val scn = scenario("Desafio")
		.exec(http("01 - Home Page")
			.get("/index.php?")
			.headers(headers_0))
		.pause(5)
		.exec(http("02 - Clica Menu Woman -> T-shirts")
			.get("/index.php?id_category=5&controller=category")
			.headers(headers_1)
			.check(regex("""<a class="button ajax_add_to_cart_button btn btn-default" href="http://automationpractice\.com/index\.php\?controller=cart\&amp;add=1\&amp;id_product=1\&amp;token=(.+?)" rel="nofollow" title="Add to cart" data-id-product="1">""").saveAs("token")))
		.exec { session => println(session); session }								
		.pause(0)
		.exec(http("03 - Clica botão Add chart")
			.post("/index.php?rand=1604315376961")
			.headers(headers_3)
			.formParam("controller", "cart")
			.formParam("add", "1")
			.formParam("ajax", "true")
			.formParam("qty", "1")
			.formParam("id_product", "1")
			.formParam("token", "${token}"))
		.pause(2)
		.feed(emails)
		.exec(http("04 - Clica botão Procced to chechout")
			.get("/index.php?controller=order")
			.headers(headers_1)
			.check(substring("Shopping-cart summary")))
		.pause(7)
		.exec(http("05 - Clica botão Procced to checkout 2")
			.get("/index.php?controller=order&step=1")
			.headers(headers_1).
			check(substring("Authentication")))
		.pause(0)
		.exec(http("06  - Autentica")
			.post("/index.php")
			.headers(headers_3)
			.formParam("controller", "authentication")
			.formParam("SubmitCreate", "1")
			.formParam("ajax", "true")
			.formParam("email_create", "${email}")
			.formParam("back", "http://automationpractice.com/index.php?controller=order&step=1&multi-shipping=0")
			.formParam("token", "${token}")
			.check(regex("Your personal information")))
		.pause(0)
		.exec(http("07 - Cadastra informações pessoais")
			.post("/index.php?controller=authentication")
			.headers(headers_8)
			.formParam("id_gender", "1")
			.formParam("customer_firstname", "First ")
			.formParam("customer_lastname", "Last")
			.formParam("email", "${email}")
			.formParam("passwd", "passwd")
			.formParam("days", "1")
			.formParam("months", "1")
			.formParam("years", "1987")
			.formParam("firstname", "First")
			.formParam("lastname", "Last")
			.formParam("company", "")
			.formParam("address1", "Address")
			.formParam("address2", "")
			.formParam("city", "Porto Alegre")
			.formParam("id_state", "3")
			.formParam("postcode", "88888")
			.formParam("id_country", "21")
			.formParam("other", "")
			.formParam("phone", "")
			.formParam("phone_mobile", "51991825508")
			.formParam("alias", "My address")
			.formParam("dni", "")
			.formParam("email_create", "1")
			.formParam("is_new_customer", "1")
			.formParam("back", "http://automationpractice.com/index.php?controller=order&step=1&multi-shipping=0")
			.formParam("submitAccount", "")
			.check(substring("Your delivery address")))
		.pause(0)
		.exec(http("08 - Endereço")
			.post("/index.php?controller=order")
			.headers(headers_8)
			.formParam("id_address_delivery", "401942")
			.formParam("same", "1")
			.formParam("message", "")
			.formParam("step", "2")
			.formParam("back", "")
			.formParam("processAddress", ""))
		.pause(0)
		.exec(http("09 -  Transportadora")
			.post("/index.php?controller=order&multi-shipping=")
			.headers(headers_8)
			.formParam("delivery_option[401942]", "2,")
			.formParam("cgv", "1")
			.formParam("step", "3")
			.formParam("back", "")
			.formParam("processCarrier", ""))
		.pause(4)
		.exec(http("10 - Escolha método pagamento ")
			.get("/index.php?fc=module&module=bankwire&controller=payment")
			.headers(headers_1)
			.check(substring("I confirm my order")))
		.pause(3)
		.exec(http("11 - Confirmação compra")
			.post("/index.php?fc=module&module=bankwire&controller=validation")
			.headers(headers_8)
			.formParam("currency_payement", "1")
			.check(substring("Your order on My Store is complete.")))// Your order on My Store is complete. 

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
