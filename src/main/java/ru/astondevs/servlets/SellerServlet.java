package ru.astondevs.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.astondevs.dto.SellerDto;
import ru.astondevs.service.SellerService;
import ru.astondevs.service.impl.SellerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/seller/*")
public class SellerServlet extends HttpServlet {
    private final SellerService service = new SellerServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json;

        Long id = getPathId(req);
        if (id == null) {
            List<SellerDto> list = service.getAll();
            json = mapper.writeValueAsString(list);
        } else {
            SellerDto cashier = service.get(id);
            if (cashier == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            json = mapper.writeValueAsString(cashier);
        }

        fillResponse(resp, json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        SellerDto seller = mapper.readValue(req.getReader(), SellerDto.class);
        seller = service.add(seller);
        String json = mapper.writeValueAsString(seller);
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
        SellerDto cashier = mapper.readValue(req.getReader(), SellerDto.class);
        cashier.setId(id);

        try {
            service.update(cashier);
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

    private void fillResponse(HttpServletResponse resp, String body) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(body);
        out.flush();
    }
}
