DO
$$
BEGIN
    -- Add the 'cpf' column if it does not already exist
    IF
NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = 'public'
          AND table_name = 'student'
          AND column_name = 'cpf'
    ) THEN
ALTER TABLE public.student
    ADD COLUMN cpf VARCHAR(11) NOT NULL;
END IF;

    -- Create a unique index for 'cpf' if it does not already exist
    IF
NOT EXISTS (
        SELECT 1
        FROM pg_indexes
        WHERE schemaname = 'public'
          AND tablename = 'student'
          AND indexname = 'idx_cpf_unique'
    ) THEN
CREATE UNIQUE INDEX idx_cpf_unique
    ON public.student (cpf);
END IF;
END;
$$
LANGUAGE plpgsql;