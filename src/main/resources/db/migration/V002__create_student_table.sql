DO
$$
BEGIN
    -- Check if the 'student' table exists in the 'public' schema
    IF
NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = 'public'
          AND table_name = 'student'
    ) THEN

        -- Create the 'student' table in the 'public' schema if it does not exist
CREATE TABLE public.student
(
    id         UUID         NOT NULL,
    name       VARCHAR(200) NOT NULL,
    email      VARCHAR(100) NOT NULL,
    age        BIGINT       NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    CONSTRAINT student_id_pk PRIMARY KEY (id)
)
    WITH (
        OIDS = FALSE
        );

END IF;
END;
$$
LANGUAGE plpgsql;