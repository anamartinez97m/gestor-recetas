
package views.xml

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.xml._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.api.data.Field
import play.data._
import play.core.j.PlayFormsMagicForJava._
import scala.jdk.CollectionConverters._

object user extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.XmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.XmlFormat.Appendable]](play.twirl.api.XmlFormat) with _root_.play.twirl.api.Template1[User,play.twirl.api.XmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(user: User):play.twirl.api.XmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""<?xml version="1.0" encoding="UTF-8"?>

<user>
    <name>"""),_display_(/*5.12*/user/*5.16*/.getName()),format.raw/*5.26*/("""</name>
    """),_display_(/*6.6*/_mi_parcial(user.getAge())),format.raw/*6.32*/("""
"""),format.raw/*7.1*/("""</user>"""))
      }
    }
  }

  def render(user:User): play.twirl.api.XmlFormat.Appendable = apply(user)

  def f:((User) => play.twirl.api.XmlFormat.Appendable) = (user) => apply(user)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/user.scala.xml
                  HASH: 5a993d79ac1c6473d7cf5d2eddf933e6f34d5443
                  MATRIX: 898->1|1003->14|1087->72|1099->76|1129->86|1167->99|1213->125|1240->126
                  LINES: 27->1|32->2|35->5|35->5|35->5|36->6|36->6|37->7
                  -- GENERATED --
              */
          