/*
 * this code is available under GNU GPL v3
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package info.stepanoff.trsis.lab1.servlets;

import info.stepanoff.trsis.lab1.model.DataModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "ExampleServlet", urlPatterns = ("/test"))
public class ExampleServlet extends HttpServlet {
    @Autowired
    DataModel RentalPropertyManager;

    protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try (PrintWriter out = res.getWriter()) {
            if (RentalPropertyManager.properties.isEmpty()) {
                RentalPropertyManager.addProperty("ul. Pushkina, 7 flat 58", 12, 25000);
                RentalPropertyManager.addProperty("ul. Komarova, 10 flat 60", 24, 30000);
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<style>");
            out.println("table, th, td {border:1px solid black}");
            out.println("</style>");
            out.println("<title>База Объектов</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<table style=\"width:50%\">");
            out.println("<tr>");
            out.println("<td>Addres</td>");
            out.println("<td>Month Rent</td>");
            out.println("<td>Price</td>");
            out.println("</tr>");
            out.println("<tbody>");
            var objects = RentalPropertyManager.getObjects();
            for (Map.Entry<String, DataModel.RentalProperty> object : objects){
                out.println("<tr>");
                out.println("<td>");
                out.println(object.getValue().getAddress());
                out.println("</td>");
                out.println("<td>");
                out.println(object.getValue().getMonthlyRent());
                out.println("</td>");
                out.println("<td>");
                out.println(object.getValue().getPrice());
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</tbody>");
            out.println("</table>");
            out.println("<h2>Добавление элемента</h2>");
            out.println("""
                    
                    <form action="/test" method="post">
                            <label for="address">Address:</label>
                            <input type="text" id="address" name="address" required>
                            <label for="monthlyrent">MontlyRent:</label>
                            <input type="text" id="monthlyrent" name="monthlyrent" required>
                            <label for="price">Price:</label>
                            <input type="text" id="price" name="price" required>
                            <input type="submit" value="Add Object">
                        </form>""");
            out.println("<h2>Удаление адреса</h2>");
            out.println("""
               
                    <form action="/test" method="post">
                    <input type="hidden" name="_method" value="DELETE">
                            <label for="address">Address:</label>
                            <input type="text" id="address" name="address" required>
                            <input type="submit" value="Delete Object">
                        </form>""");
            out.println("<h2>Изменение объекта</h2>");
            out.println("""
               
                    <form action="/test" method="post">
                    <input type="hidden" name="_method" value="PUT">
                            <label for="address">Address:</label>
                            <input type="text" id="address" name="address" required>
                            <label for="monthlyrent">MontlyRent:</label>
                            <input type="text" id="monthlyrent" name="monthlyrent" required>
                            <label for="price">Price:</label>
                            <input type="text" id="price" name="price" required>
                            <input type="submit" value="Put Price">
                        </form>""");
            out.println("</body>");
            out.println("</html>");
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        processRequest(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!(request.getParameterMap().containsKey("_method"))){
            String address = request.getParameterMap().get("address")[0];
            int montlyrent = Integer.parseInt(request.getParameterMap().get("monthlyrent")[0]);
            double price = Double.parseDouble(request.getParameterMap().get("price")[0]);
            RentalPropertyManager.addProperty(address, montlyrent, price);
            processRequest(request, response);
        }
        else if ("DELETE".equals(request.getParameterMap().get("_method")[0])){
            doDelete(request, response);
        }
        else if ("PUT".equals(request.getParameterMap().get("_method")[0])){
            doPut(request, response);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String address = request.getParameterMap().get("address")[0];
        RentalPropertyManager.remove(address);
        processRequest(request, response);
    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String address = request.getParameterMap().get("address")[0];
        double price = Double.parseDouble(request.getParameterMap().get("price")[0]);
        int montlyrent = Integer.parseInt(request.getParameterMap().get("monthlyrent")[0]);
        RentalPropertyManager.put_obj(address, price, montlyrent);
        processRequest(request, response);
    }

}

