CREATE TABLE resume
(
    uuid      CHAR(36) NOT NULL,
    full_name TEXT     NOT NULL,
    CONSTRAINT resume_pk PRIMARY KEY (uuid)
);

CREATE TABLE contact
(
    id          SERIAL,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL,
    resume_uuid CHAR(36) NOT NULL,
    CONSTRAINT contact_resume_fk FOREIGN KEY (resume_uuid)
        REFERENCES resume (uuid)
        ON DELETE CASCADE
);

CREATE UNIQUE INDEX contact_uuid_type_index
    on contact (resume_uuid, type);


