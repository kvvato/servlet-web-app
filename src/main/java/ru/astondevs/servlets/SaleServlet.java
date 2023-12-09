package ru.astondevs.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.astondevs.dto.SaleDTO;
import ru.astondevs.mapper.SaleMapper;
import ru.astondevs.mapper.impl.SaleMapperImpl;
import ru.astondevs.repository.SaleRepository;
import ru.astondevs.util.DbConnector;
import ru.astondevs.repository.impl.SaleRepositoryImpl;
import ru.astondevs.service.SaleService;
import ru.astondevs.service.impl.SaleServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/sale/*")
public class SaleServlet extends HttpServlet {
    SaleService service;

    public SaleServlet() {
        DbConnector connector = new DbConnector();
        SaleRepository repository = new SaleRepositoryImpl(connector);
        SaleMapper mapper = new SaleMapperImpl();
        service = new SaleServiceImpl(repository, mapper);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json;

        try {
            Long id = getPathId(req);
            if (id == null) {
                List<SaleDTO> list = getSales(req);
                json = mapper.writeValueAsString(list);
            } else {
                SaleDTO sale = service.get(id);
                if (sale == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                json = mapper.writeValueAsString(sale);
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
        SaleDTO sale = mapper.readValue(req.getReader(), SaleDTO.class);
        service.add(sale);
        String json = mapper.writeValueAsString(sale);
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
        SaleDTO sale = mapper.readValue(req.getReader(), SaleDTO.class);
        sale.setId(id);

        try {
            service.update(sale);
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

    private List<SaleDTO> getSales(HttpServletRequest req) throws NumberFormatException {
        String seller = req.getParameter("seller");
        if (seller != null) {
            return service.getBySeller(Long.parseLong(seller));
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
