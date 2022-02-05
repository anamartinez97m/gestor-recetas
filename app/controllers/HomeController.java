package controllers;

import java.util.ArrayList;
import java.util.Optional;

import javax.inject.Inject;
import javax.swing.text.AbstractDocument.Content;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
// import play.mvc.*;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

public class HomeController extends Controller {

    //private static ArrayList<User> users = new ArrayList<User>();

    // @Inject
    // private FormFactory formFactory;

    // public Result getAllUsers() {
    //     return ok("Todos los usuarios");
    // }

    // public Result getOneUser(Http.Request request, String userId) {
    //     User userFound = User.findUserbyId(Long.valueOf(userId));

    //     if(userFound == null) {
    //         return Results.notFound();
    //     }

    //     if(request.accepts("application/xml")) {
            
    //         Content content = views.xml.user.render(userFound);
    //         return Results.ok(content);

    //     } else if(request.accepts("application/json")) {
            
    //         return ok(Json.toJson(userFound));

    //     } else {
    //         ObjectNode result = Json.newObject();
    //         result.put("error", "Unssupported format");
    //         return Results.status(406, result);
    //     }      
    // }

    // public Result createUser(Http.Request request) {
    //     // 1. Leer el texto del body
    //     // 2. Leer la cabecera Content-Type de la request
    //     // 3. Utiliza el parseador correspondiente al content-type para parsear el body
    //     // 4. Una vez parseado, mapea y rellena los valores del objecto resultado
    //     Form<User> userForm = formFactory.form(User.class).bindFromRequest(request);

    //     if(userForm.hasErrors()) {
    //         JsonNode errors = userForm.errorsAsJson();
    //         return Results.notAcceptable(errors);
    //     }

    //     User user = userForm.get();
        
    //     user.save();

    //     UserBio bio = new UserBio();
    //     bio.setTexto("blablabla");

    //     user.setBio(bio);

    //     UserAddress address1 = new UserAddress();
    //     address1.setStreet("sesame street");
    //     user.addAddress(address1);

    //     UserAddress address2 = new UserAddress();
    //     address1.setStreet("plaza mayor");
    //     user.addAddress(address2);


    //     JsonNode node = Json.toJson(user);

    //     return ok(node)
    //             .withHeader("mi-cabecera", "mi-valor")
    //             .as("application/json");
    // }

    // private JsonNode getUserJson(String nombre, String apellido) {
    //     ObjectNode result = Json.newObject();
    //     result.put("nombre", nombre);
    //     result.put("apellido", apellido);
    //     result.set("un-array", Json.newArray().add(nombre).add(apellido));

    //     return result;
    // }
 
}
