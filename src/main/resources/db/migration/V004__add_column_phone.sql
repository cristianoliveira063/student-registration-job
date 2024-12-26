DO
$$
BEGIN
    -- Add 'phone' column if it doesn't exist
    IF
NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_schema = 'public'
          AND table_name = 'student'
          AND column_name = 'phone'
    ) THEN
ALTER TABLE public.student
    ADD COLUMN phone VARCHAR(15);
END IF;
END;
$$
LANGUAGE plpgsql;