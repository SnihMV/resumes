package ru.msnih.resumes.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.msnih.resumes.model.*;
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

        Resume resume;
        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                resp.sendRedirect("list");
                return;
            }
            case "view" -> {
                resume = storage.get(uuid);
            }
            case "edit" -> {
                resume = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    if (resume.getSection(type) == null) {
                        switch (type) {
                            case OBJECTIVE, PERSONAL -> resume.addSection(type, TextSection.EMPTY);
                            case QUALIFICATIONS, ACHIEVEMENTS -> resume.addSection(type, ListSection.EMPTY);
                            case EDUCATION, EXPERIENCE ->resume.addSection(type, OrganizationSection.EMPTY);
                        }
                    }
                }
            }
            default -> throw new IllegalStateException("Illegal action: " + action);
        }
        req.setAttribute("resume", resume);
        req.getRequestDispatcher(action.equals("view") ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Resume resume = new Resume(req.getParameter("uuid"), req.getParameter("fullName"));
        for (ContactType type : ContactType.values()) {
            String value = req.getParameter(type.name()).trim();
            if (!value.isEmpty()) {
                resume.addContact(type, value);
            }
        }
        storage.update(resume);
        resp.sendRedirect("list");
    }
}
