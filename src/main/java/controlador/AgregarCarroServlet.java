package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Carro;
import modelo.ItemCarro;
import modelo.Productos;
import servicio.ProductoService;
import servicio.ProductoServiceImplement;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/agregar-carro")
public class AgregarCarroServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long idProducto = Long.parseLong(req.getParameter("idProducto"));
        ProductoService service = new ProductoServiceImplement();
        Optional<Productos> producto=service.agregarPorId(idProducto);
        if(producto.isPresent()) {
            ItemCarro item = new ItemCarro(1,producto.get());
            HttpSession session = req.getSession();
            Carro carro;
            if(session.getAttribute("carro") == null) {
                carro = new Carro();
                session.setAttribute("carro", carro);
            }else{
                carro = (Carro)session.getAttribute("carro");
            }
            carro.addItemCarro(item);
        }
        resp.sendRedirect(req.getContextPath()+"/ver-carro");
    }
}
