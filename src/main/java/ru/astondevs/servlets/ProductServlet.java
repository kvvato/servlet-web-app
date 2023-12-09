package ru.astondevs.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.astondevs.dto.ProductDTO;
import ru.astondevs.dto.SaleDTO;
import ru.astondevs.mapper.ProductMapper;
import ru.astondevs.mapper.impl.ProductMapperImpl;
import ru.astondevs.repository.ProductRepository;
import ru.astondevs.util.DbConnector;
import ru.astondevs.repository.impl.ProductRepositoryImpl;
import ru.astondevs.service.ProductService;
import ru.astondevs.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {
    ProductService service;

    public ProductServlet() {
        DbConnector connector = new DbConnector();
        ProductRepository repository = new ProductRepositoryImpl(connector);
        ProductMapper mapper = new ProductMapperImpl();
        service = new ProductServiceImpl(repository, mapper);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json;

        try {
            Long id = getPathId(req);
            if (id == null) {
                List<ProductDTO> list = getProducts(req);
                json = mapper.writeValueAsString(list);
            } else {
                ProductDTO product = service.get(id);
                if (product == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                json = mapper.writeValueAsString(product);
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        fillResponse(resp, json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        ProductDTO product = mapper.readValue(req.getReader(), ProductDTO.class);
        service.add(product);
        String json = mapper.writeValueAsString(product);
        fillResponse(resp, json);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = getPathId(req);
        if (id == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        ProductDTO product = mapper.readValue(req.getReader(), ProductDTO.class);
        product.setId(id);

        try {
            service.update(product);
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = getPathId(req);
        if (id == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            service.remove(id);
        } catch (SQLException ex) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private Long getPathId(HttpServletRequest req) throws NumberFormatException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            return null;
        }

        Long id = null;
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length == 2) {
            id = Long.parseLong(pathParts[1]);
        }

        return id;
    }

    private List<ProductDTO> getProducts(HttpServletRequest req) throws NumberFormatException {
        String seller = req.getParameter("seller");
        if (seller != null) {
            return service.getSoldProductsBySeller(Long.parseLong(seller));
        }
        return service.getAll();
    }

    private void fillResponse(HttpServletResponse resp, String body) throws IOException{
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(body);
        out.flush();
    }
}
