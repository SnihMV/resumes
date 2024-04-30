CREATE TABLE resume
(
    uuid      CHAR(36) NOT NULL,
    full_name TEXT     NOT NULL,
    CONSTRAINT resume_pk PRIMARY KEY (uuid)
);

CREATE TABLE contact
(
    id          SERIAL,
    resume_uuid CHAR(36) NOT NULL,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL,
    CONSTRAINT contact_resume_fk FOREIGN KEY (resume_uuid)
        REFERENCES resume (uuid)
        ON DELETE CASCADE
);

CREATE UNIQUE INDEX contact_uuid_type_index
    on contact (resume_uuid, type);

CREATE TABLE section
(
    id          CHAR(36) PRIMARY KEY,
    resume_uuid CHAR(36) REFERENCES resume ON DELETE CASCADE,
    type        TEXT NOT NULL
);

CREATE INDEX section_uuid_index ON section (resume_uuid, type);

CREATE TABLE text_section_entry
(
    id         SERIAL PRIMARY KEY,
    section_id CHAR(36) REFERENCES section ON DELETE CASCADE,
    content    TEXT NOT NULL
);

CREATE INDEX text_content_section_index ON text_section_entry (section_id);


CREATE TABLE organization
(
    id         CHAR(36) PRIMARY KEY,
    section_id CHAR(36) REFERENCES section ON DELETE CASCADE,
    name       TEXT NOT NULL,
    url        TEXT
);

CREATE INDEX organization_section_index ON organization (section_id);

CREATE TABLE position
(
    id              SERIAL PRIMARY KEY,
    organization_id CHAR(36) REFERENCES organization ON DELETE CASCADE,
    title           TEXT NOT NULL,
    description     TEXT,
    start_date      DATE NOT NULL,
    end_date        DATE
);

CREATE INDEX position_organization_index ON position (organization_id);





