--liquibase formatted sql
--changeset <macbook>:<create-movie-characters-sequence-id>
CREATE SEQUENCE IF NOT EXISTS public.movie_characters_id_seq INCREMENT 1 START 1 MINVALUE 1;
--rollback DROP SEQUENCE public.movie_characters_id_seq;