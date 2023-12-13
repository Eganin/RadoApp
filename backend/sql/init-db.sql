CREATE TABLE drivers
(
    id serial,
    full_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    phone character varying(12) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT drivers_pkey PRIMARY KEY (id),
    CONSTRAINT driver_full_name UNIQUE (full_name),
    CONSTRAINT driver_phone UNIQUE (phone)
);

CREATE TABLE vehicles
(
    id serial,
    type_vehicle character varying(20) COLLATE pg_catalog."default" NOT NULL,
    number_vehicle character varying(20) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT vehicles_pkey PRIMARY KEY (id)
);

CREATE TABLE mechanics
(
    id serial,
    full_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    phone character varying(12) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT mechanics_pkey PRIMARY KEY (id),
    CONSTRAINT mechanic_full_name UNIQUE (full_name),
    CONSTRAINT mechanic_phone UNIQUE (phone)
);

CREATE TABLE observers
(
    id serial,
    full_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    phone character varying(12) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT observers_pkey PRIMARY KEY (id),
    CONSTRAINT observer_full_name UNIQUE (full_name),
    CONSTRAINT observer_phone UNIQUE (phone)
);

CREATE TABLE requests
(
    id serial,
    vehicle_id integer NOT NULL,
    driver_id integer NOT NULL,
    mechanic_id integer,
    fault_description text COLLATE pg_catalog."default",
    status_repair boolean,
    comment_mechanic text COLLATE pg_catalog."default",
    status_request character varying(20) COLLATE pg_catalog."default",
    date character varying(10) COLLATE pg_catalog."default",
    "time" character varying(5) COLLATE pg_catalog."default",
    arrival_date character varying(10) COLLATE pg_catalog."default",
    street_repair character varying(100) COLLATE pg_catalog."default",
    test_migrate character varying(100) COLLATE pg_catalog."default",
    CONSTRAINT requests_pkey PRIMARY KEY (id),
    CONSTRAINT mechanic_id FOREIGN KEY (mechanic_id)
        REFERENCES public.mechanics (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT request_driver_id FOREIGN KEY (driver_id)
        REFERENCES public.drivers (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT request_vehicle_id FOREIGN KEY (vehicle_id)
        REFERENCES public.vehicles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE fault_images
(
    id serial,
    image_path character varying(100) COLLATE pg_catalog."default" NOT NULL,
    request_id integer NOT NULL,
    CONSTRAINT fault_images_pkey PRIMARY KEY (id),
    CONSTRAINT fault_image_path UNIQUE (image_path),
    CONSTRAINT image_request_id FOREIGN KEY (request_id)
        REFERENCES public.requests (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

CREATE TABLE fault_videos
(
    id serial,
    video_path character varying(100) COLLATE pg_catalog."default" NOT NULL,
    request_id integer NOT NULL,
    CONSTRAINT fault_videos_pkey PRIMARY KEY (id),
    CONSTRAINT fault_video_path UNIQUE (video_path),
    CONSTRAINT video_request_id FOREIGN KEY (request_id)
        REFERENCES public.requests (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);