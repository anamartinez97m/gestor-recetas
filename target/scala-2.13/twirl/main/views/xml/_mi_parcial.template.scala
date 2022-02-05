
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

object _mi_parcial extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.XmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.XmlFormat.Appendable]](play.twirl.api.XmlFormat) with _root_.play.twirl.api.Template1[Int,play.twirl.api.XmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(edad: Int):play.twirl.api.XmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/if(edad > 0)/*3.14*/ {_display_(Seq[Any](format.raw/*3.16*/("""
    """),format.raw/*4.5*/("""<age>"""),_display_(/*4.11*/age),format.raw/*4.14*/("""</age>
""")))}))
      }
    }
  }

  def render(edad:Int): play.twirl.api.XmlFormat.Appendable = apply(edad)

  def f:((Int) => play.twirl.api.XmlFormat.Appendable) = (edad) => apply(edad)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/_mi_parcial.scala.xml
                  HASH: d88e42ebaf732f89af0d7f6624925c2c3e691066
                  MATRIX: 904->1|1008->13|1035->15|1055->27|1094->29|1125->34|1157->40|1180->43
                  LINES: 27->1|32->2|33->3|33->3|33->3|34->4|34->4|34->4
                  -- GENERATED --
              */
          