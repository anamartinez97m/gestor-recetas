// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset
import _root_.play.libs.F

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  HomeController_0: controllers.HomeController,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    HomeController_0: controllers.HomeController
  ) = this(errorHandler, HomeController_0, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """usuarios""", """controllers.HomeController.getAllUsers()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """usuario/""" + "$" + """userId<[^/]+>""", """controllers.HomeController.getOneUser(request:Request, userId:String)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """usuarios""", """controllers.HomeController.createUser(request:Request)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val controllers_HomeController_getAllUsers0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("usuarios")))
  )
  private[this] lazy val controllers_HomeController_getAllUsers0_invoker = createInvoker(
    HomeController_0.getAllUsers(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "getAllUsers",
      Nil,
      "GET",
      this.prefix + """usuarios""",
      """ An example controller showing a sample home page""",
      Seq()
    )
  )

  // @LINE:7
  private[this] lazy val controllers_HomeController_getOneUser1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("usuario/"), DynamicPart("userId", """[^/]+""",true)))
  )
  private[this] lazy val controllers_HomeController_getOneUser1_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      HomeController_0.getOneUser(fakeValue[play.mvc.Http.Request], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "getOneUser",
      Seq(classOf[play.mvc.Http.Request], classOf[String]),
      "GET",
      this.prefix + """usuario/""" + "$" + """userId<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:9
  private[this] lazy val controllers_HomeController_createUser2_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("usuarios")))
  )
  private[this] lazy val controllers_HomeController_createUser2_invoker = createInvoker(
    
    (req:play.mvc.Http.Request) =>
      HomeController_0.createUser(fakeValue[play.mvc.Http.Request]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "createUser",
      Seq(classOf[play.mvc.Http.Request]),
      "POST",
      this.prefix + """usuarios""",
      """""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case controllers_HomeController_getAllUsers0_route(params@_) =>
      call { 
        controllers_HomeController_getAllUsers0_invoker.call(HomeController_0.getAllUsers())
      }
  
    // @LINE:7
    case controllers_HomeController_getOneUser1_route(params@_) =>
      call(params.fromPath[String]("userId", None)) { (userId) =>
        controllers_HomeController_getOneUser1_invoker.call(
          req => HomeController_0.getOneUser(req, userId))
      }
  
    // @LINE:9
    case controllers_HomeController_createUser2_route(params@_) =>
      call { 
        controllers_HomeController_createUser2_invoker.call(
          req => HomeController_0.createUser(req))
      }
  }
}
