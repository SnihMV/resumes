INSERT INTO resume (uuid, full_name)
VALUES ('f0494bf2-c793-4ef6-9a2a-071363f7b5b4', 'Sergey'),
       ('35647749-02c3-4633-ad82-2f1f2c6afd95', 'Alex'),
       ('be409d5e-5f44-4b9b-8521-c955f837f8ba', 'Mike'),
       ('d495e2c0-08cd-4c34-b69e-697850ab74df', 'Pavel'),
       ('cae3fb4e-a188-4561-9eb1-b39f41e3564e', 'Dmitriy');

INSERT INTO contact (resume_uuid, type, value)
VALUES ('be409d5e-5f44-4b9b-8521-c955f837f8ba', 'PHONE', '+79219894856'),
       ('be409d5e-5f44-4b9b-8521-c955f837f8ba', 'EMAIL', 'snihmv@gmail.com'),
       ('be409d5e-5f44-4b9b-8521-c955f837f8ba', 'SKYPE', 'sneechSkype'),
       ('35647749-02c3-4633-ad82-2f1f2c6afd95', 'PHONE', '+79326548715'),
       ('35647749-02c3-4633-ad82-2f1f2c6afd95', 'LINKEDIN', 'PaulLinkedin'),
       ('35647749-02c3-4633-ad82-2f1f2c6afd95', 'EMAIL', 'gg@tl.net'),
       ('cae3fb4e-a188-4561-9eb1-b39f41e3564e', 'STACKOVERFLOW', 'DimanSOF'),
       ('cae3fb4e-a188-4561-9eb1-b39f41e3564e', 'HOMEPAGE', 'Dimaga.net');
