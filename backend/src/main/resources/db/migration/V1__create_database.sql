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