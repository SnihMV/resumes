package ru.msnih.resumes.storage;

import ru.msnih.resumes.exception.NotExistStorageException;
import ru.msnih.resumes.model.*;
import ru.msnih.resumes.sql.LineProcessor;
import ru.msnih.resumes.sql.SqlHelper;
import ru.msnih.resumes.util.JsonParser;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }


    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count");
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT full_name FROM resume WHERE uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                Resume resume = new Resume(uuid, rs.getString("full_name"));
                addContacts(resume, conn);
                addSections(resume, conn);
//                addJsonSection(resume, conn);
                return resume;
            }
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContacts(resume, conn);
            insertSections(resume, conn);
//            insertJsonSection(resume, conn);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
                deleteContacts(resume, conn);
                deleteSections(resume, conn);
//                deleteJsonSection(resume, conn);
                insertContacts(resume, conn);
                insertSections(resume, conn);
//                insertJsonSection(resume, conn);
            }

            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                Map<String, Resume> resumes = new LinkedHashMap<>();
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    String fullName = rs.getString("full_name");
                    Resume resume = new Resume(uuid, fullName);
                    addContacts(resume, conn);
                    addSections(resume, conn);
//                    addJsonSection(resume, conn);
                    resumes.put(uuid, resume);
                }
                return new ArrayList<>(resumes.values());
            }
        });
    }


    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    private static void insertContacts(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, contact.getKey().name());
                ps.setString(3, contact.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContacts(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT type, value FROM contact WHERE resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                addContact(resume, rs);
            }
        }
    }

    private void deleteContacts(Resume resume, Connection conn) throws SQLException {
        deleteAttributes(resume, "DELETE FROM contact WHERE resume_uuid = ?", conn);
    }

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        if (type != null) {
            resume.addContact(ContactType.valueOf(type), rs.getString("value"));
        }
    }

    private void insertSections(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (id, resume_uuid, type) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> sectionEntry : resume.getSections().entrySet()) {
                String sectionId = UUID.randomUUID().toString();
                ps.setString(1, sectionId);
                ps.setString(2, resume.getUuid());
                ps.setString(3, sectionEntry.getKey().name());
                ps.execute();
                switch (sectionEntry.getKey()) {
                    case PERSONAL, OBJECTIVE ->
                            insertTextSection(sectionId, (TextSection) sectionEntry.getValue(), conn);
                    case ACHIEVEMENTS, QUALIFICATIONS ->
                            insertListSection(sectionId, (ListSection) sectionEntry.getValue(), conn);
                    case EDUCATION, EXPERIENCE ->
                            insertOrganizationSection(sectionId, (OrganizationSection) sectionEntry.getValue(), conn);
                }
            }
        }
    }

    private void addSections(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT id, type FROM section WHERE resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String sectionId = resultSet.getString("id");
                SectionType type = SectionType.valueOf(resultSet.getString("type"));
                switch (type) {
                    case PERSONAL, OBJECTIVE ->
                            readFromTable(conn, "SELECT content FROM text_section_entry WHERE section_id = ?",
                                    sectionId, (rs) -> resume.addSection(
                                            type, new TextSection(rs.getString("content"))));
                    case ACHIEVEMENTS, QUALIFICATIONS -> {
                        ListSection section = new ListSection();
                        readFromTable(conn, "SELECT content FROM text_section_entry WHERE section_id = ?",
                                sectionId, (rs) -> section.addItem(rs.getString("content")));
                        resume.addSection(type, section);
                    }
                    case EDUCATION, EXPERIENCE -> {
                        OrganizationSection section = new OrganizationSection();
                        readFromTable(conn, "SELECT id, name, url FROM organization WHERE section_id = ?",
                                sectionId, (rs) -> {
                                    Organization organization = new Organization(
                                            rs.getString("name"), rs.getString("url"));
                                    readFromTable(conn, """
                                                    SELECT title, description, start_date, end_date 
                                                    FROM position WHERE organization_id = ?""",
                                            rs.getString("id"), (rs1) -> {
                                                Date date = rs1.getDate("end_date");
                                                LocalDate endDate = date != null ? date.toLocalDate() : null;
                                                organization.addPosition(new Organization.Position(
                                                        rs1.getString("title"),
                                                        rs1.getString("description"),
                                                        rs1.getDate("start_date").toLocalDate(),
                                                        endDate));
                                            });
                                    section.addOrganization(organization);
                                });
                        resume.addSection(type, section);
                    }
                }
            }
        }
    }

    private void insertTextSection(String sectionId, TextSection section, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO text_section_entry (section_id, content) VALUES (?,?)")) {
            ps.setString(1, sectionId);
            ps.setString(2, section.getContent());
            ps.execute();
        }
    }

    private void insertListSection(String sectionId, ListSection section, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO text_section_entry (section_id, content) VALUES (?,?)")) {
            for (String entry : section.getList()) {
                ps.setString(1, sectionId);
                ps.setString(2, entry);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertOrganizationSection(String sectionId, OrganizationSection section, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO organization  VALUES (?,?,?,?)")) {
            for (Organization organization : section.getOrganizations()) {
                String organizationId = UUID.randomUUID().toString();
                ps.setString(1, organizationId);
                ps.setString(2, sectionId);
                ps.setString(3, organization.getLink().getName());
                ps.setString(4, organization.getLink().getUrl());
                ps.execute();
                insertPositions(organizationId, organization, conn);
            }
        }
    }

    private void insertPositions(String organizationId, Organization organization, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("""
                INSERT INTO position (organization_id, title, description, start_date, end_date) 
                VALUES (?,?,?,?,?)""")) {
            for (Organization.Position position : organization.getPositions()) {
                ps.setString(1, organizationId);
                ps.setString(2, position.getTitle());
                ps.setString(3, position.getDescription());
                ps.setDate(4, Date.valueOf(position.getStartDate()));
                ps.setDate(5, position.getEndDate() != null ? Date.valueOf(position.getEndDate()) : null);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteSections(Resume resume, Connection conn) throws SQLException {
        deleteAttributes(resume, "DELETE FROM section WHERE resume_uuid = ?", conn);
    }

    private void deleteAttributes(Resume resume, String sql, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void readFromTable(Connection conn, String sql, String id, LineProcessor lineProcessor) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lineProcessor.process(rs);
            }
        }
    }

    private void addJsonSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT type, content FROM json_section WHERE resume_uuid =?")) {
            ps.setString(1, resume.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resume.addSection(SectionType.valueOf(rs.getString("type")), JsonParser.read(rs.getString("content"), Section.class));
            }
        }
    }

    private void insertJsonSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO json_section (resume_uuid, type, content) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, JsonParser.write(entry.getValue(), Section.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteJsonSection(Resume resume, Connection conn) throws SQLException {
        deleteAttributes(resume, "DELETE FROM json_section WHERE resume_uuid = ?", conn);
    }

   /* @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("""
                SELECT uuid, full_name, type, value FROM resume r
                LEFT JOIN contact c ON uuid = resume_uuid
                ORDER BY full_name, uuid""", ps -> {
            Map<String, Resume> resumeMap = new LinkedHashMap<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                Resume resume = resumeMap.getOrDefault(uuid, new Resume(uuid, rs.getString("full_name")));
                addContact(resume, rs);
                resumeMap.put(uuid, resume);
            }
            return new ArrayList<>(resumeMap.values());
        });
    }*/


}
