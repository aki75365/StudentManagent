-- students
INSERT INTO students (id, name, gender, age, birth_date, email)
VALUES
 (1, '山田太郎',   'M', 35, '1990-01-01', 'taro.yamada@example.com'),
 (2, '山田健太',   'M', 22, '2003-03-15', 'kenta.yamada@example.com'),
 (3, '佐藤花子',   'F', 29, '1996-07-10', 'hanako.sato@example.com'),
 (4, '田中花子',   'F', 31, '1994-05-21', 'hanako.tanaka@example.com'),
 (5, '鈴木一郎',   'M', 40, '1985-11-02', 'ichiro.suzuki@example.com'),
 (6, '佐藤美咲',   'F', 33, '1992-09-09', 'misaki.sato@example.com');

-- courses
INSERT INTO courses (id, name, category, description)
VALUES
 (1, 'Java入門',   'Java',   'Basics of Java'),
 (2, 'Java応用',   'Java',   'Advanced Java'),
 (3, 'Python基礎', 'Python', 'Intro to Python');

-- student_courses
INSERT INTO student_courses (student_id, course_id, enrolled_at) VALUES
 (1, 1, '2024-01-10'),
 (1, 2, '2024-02-10'),
 (2, 1, '2024-03-10'),
 (3, 1, '2024-04-10'),
 (4, 2, '2024-05-10'),
 (5, 3, '2024-06-10'),
 (6, 1, '2024-07-10'),
 (6, 2, '2024-07-20');
