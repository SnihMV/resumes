package ru.msnih.resumes.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.msnih.resumes.storage.Storage;
import ru.msnih.resumes.util.Config;

import java.io.IOException;

@WebServlet(urlPatterns = {"/resume"})
public class ResumeServlet extends HttpServlet {
    private final Storage storage = Config.getInstance().getStorage();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        String action = req.getParameter("action");

        if (action.equals("delete")) {
            storage.delete(uuid);
            resp.sendRedirect("list");
            return;
        }
        req.setAttribute("resume", storage.get(uuid));
        req.getRequestDispatcher(switch (action) {
            case "view" -> "/WEB-INF/jsp/view.jsp";
            case "edit" -> "/WEB-INF/jsp/edit.jsp";
            default -> throw new IllegalStateException("Illegal parameter: action=\"" + action + "\"");
        }).forward(req, resp);

    }
}
