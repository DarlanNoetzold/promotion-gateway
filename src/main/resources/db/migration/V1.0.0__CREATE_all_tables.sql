-- Table: public.promotionmodel

-- DROP TABLE IF EXISTS public.promotionmodel;

CREATE TABLE IF NOT EXISTS public.promotionmodel
(
    promoid uuid NOT NULL,
    applytocode character varying(255) COLLATE pg_catalog."default",
    discountlimit double precision,
    enddate timestamp(6) without time zone,
    exceptionparentid character varying(255) COLLATE pg_catalog."default",
    promocompdesc character varying(255) COLLATE pg_catalog."default",
    promocomptype double precision,
    promodescription character varying(255) COLLATE pg_catalog."default",
    promodtlid double precision,
    promoname character varying(255) COLLATE pg_catalog."default",
    promotiontype smallint,
    rules character varying(255) COLLATE pg_catalog."default",
    startdate timestamp(6) without time zone,
    value double precision,
    valuetype smallint,
    CONSTRAINT promotionmodel_pkey PRIMARY KEY (promoid),
    CONSTRAINT promotionmodel_promotiontype_check CHECK (promotiontype >= 0 AND promotiontype <= 2),
    CONSTRAINT promotionmodel_valuetype_check CHECK (valuetype >= 0 AND valuetype <= 1)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.promotionmodel
    OWNER to postgres;

-- Table: public.couponmodel

-- DROP TABLE IF EXISTS public.couponmodel;

CREATE TABLE IF NOT EXISTS public.couponmodel
(
    couponid uuid NOT NULL,
    code character varying(255) COLLATE pg_catalog."default",
    promoid uuid,
    CONSTRAINT couponmodel_pkey PRIMARY KEY (couponid),
    CONSTRAINT fk17ndc6fy9e4a1o7alqqe32tb8 FOREIGN KEY (promoid)
        REFERENCES public.promotionmodel (promoid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.couponmodel
    OWNER to postgres;