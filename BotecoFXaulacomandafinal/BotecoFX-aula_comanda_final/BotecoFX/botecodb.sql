--
-- PostgreSQL database dump
--

-- Dumped from database version 15.5
-- Dumped by pg_dump version 15.5

-- Started on 2024-05-09 19:58:09

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 214 (class 1259 OID 16399)
-- Name: categoria; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoria (
    cat_id integer NOT NULL,
    cat_nome character varying(15) NOT NULL
);


ALTER TABLE public.categoria OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16402)
-- Name: categoria_cat_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categoria_cat_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categoria_cat_id_seq OWNER TO postgres;

--
-- TOC entry 3377 (class 0 OID 0)
-- Dependencies: 215
-- Name: categoria_cat_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoria_cat_id_seq OWNED BY public.categoria.cat_id;


--
-- TOC entry 216 (class 1259 OID 16403)
-- Name: comanda; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comanda (
    com_id integer NOT NULL,
    gar_id integer NOT NULL,
    com_numero numeric(15,0) NOT NULL,
    com_nome character varying(40),
    com_data date,
    com_desc character varying(255) NOT NULL,
    com_valor numeric(8,2),
    com_status character(1)
);


ALTER TABLE public.comanda OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16406)
-- Name: comanda_com_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.comanda_com_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.comanda_com_id_seq OWNER TO postgres;

--
-- TOC entry 3378 (class 0 OID 0)
-- Dependencies: 217
-- Name: comanda_com_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.comanda_com_id_seq OWNED BY public.comanda.com_id;


--
-- TOC entry 218 (class 1259 OID 16407)
-- Name: garcon; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.garcon (
    gar_id integer NOT NULL,
    gar_nome character varying(40) NOT NULL,
    gar_cpf character varying(14) NOT NULL,
    gar_cep character varying(10),
    gar_endereco character varying(50),
    gar_cidade character varying(20),
    gar_uf character varying(2),
    gar_fone character varying(15),
    gar_foto bytea
);


ALTER TABLE public.garcon OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16412)
-- Name: garcon_gar_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.garcon_gar_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.garcon_gar_id_seq OWNER TO postgres;

--
-- TOC entry 3379 (class 0 OID 0)
-- Dependencies: 219
-- Name: garcon_gar_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.garcon_gar_id_seq OWNED BY public.garcon.gar_id;


--
-- TOC entry 220 (class 1259 OID 16413)
-- Name: item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.item (
    com_id integer NOT NULL,
    prod_id integer NOT NULL,
    it_quantidade integer NOT NULL
);


ALTER TABLE public.item OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16420)
-- Name: produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.produto (
    prod_id integer NOT NULL,
    cat_id integer NOT NULL,
    uni_id integer NOT NULL,
    prod_nome character varying(30) NOT NULL,
    prod_preco numeric(8,2) NOT NULL,
    prod_descr character varying(100)
);


ALTER TABLE public.produto OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16423)
-- Name: produto_prod_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.produto_prod_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.produto_prod_id_seq OWNER TO postgres;

--
-- TOC entry 3380 (class 0 OID 0)
-- Dependencies: 222
-- Name: produto_prod_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.produto_prod_id_seq OWNED BY public.produto.prod_id;


--
-- TOC entry 223 (class 1259 OID 16428)
-- Name: unidade; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.unidade (
    uni_id integer NOT NULL,
    uni_nome character varying(15) NOT NULL
);


ALTER TABLE public.unidade OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16431)
-- Name: unidade_uni_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.unidade_uni_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.unidade_uni_id_seq OWNER TO postgres;

--
-- TOC entry 3381 (class 0 OID 0)
-- Dependencies: 224
-- Name: unidade_uni_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.unidade_uni_id_seq OWNED BY public.unidade.uni_id;


--
-- TOC entry 3197 (class 2604 OID 16432)
-- Name: categoria cat_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria ALTER COLUMN cat_id SET DEFAULT nextval('public.categoria_cat_id_seq'::regclass);


--
-- TOC entry 3198 (class 2604 OID 16433)
-- Name: comanda com_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comanda ALTER COLUMN com_id SET DEFAULT nextval('public.comanda_com_id_seq'::regclass);


--
-- TOC entry 3199 (class 2604 OID 16434)
-- Name: garcon gar_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.garcon ALTER COLUMN gar_id SET DEFAULT nextval('public.garcon_gar_id_seq'::regclass);


--
-- TOC entry 3200 (class 2604 OID 16436)
-- Name: produto prod_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto ALTER COLUMN prod_id SET DEFAULT nextval('public.produto_prod_id_seq'::regclass);


--
-- TOC entry 3201 (class 2604 OID 16438)
-- Name: unidade uni_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidade ALTER COLUMN uni_id SET DEFAULT nextval('public.unidade_uni_id_seq'::regclass);


--
-- TOC entry 3361 (class 0 OID 16399)
-- Dependencies: 214
-- Data for Name: categoria; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categoria VALUES (1, 'Bebida');
INSERT INTO public.categoria VALUES (3, 'Sobremesa');
INSERT INTO public.categoria VALUES (2, 'Prato');
INSERT INTO public.categoria VALUES (4, 'Porção');


--
-- TOC entry 3363 (class 0 OID 16403)
-- Dependencies: 216
-- Data for Name: comanda; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.comanda VALUES (1, 1, 123, 'amigo do dono', '2024-05-05', 'mesa do salao 2', 100.00, 'F');


--
-- TOC entry 3365 (class 0 OID 16407)
-- Dependencies: 218
-- Data for Name: garcon; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.garcon VALUES (1, 'Fábio', '666.111.222-12', '14154960', 'Rua Tupinambás nº:32', 'Epitácio', 'SP', '(18)99885-6332', NULL);
INSERT INTO public.garcon VALUES (2, 'Uilbor', '121.555.96-99', '19013010', 'Rua Dr José Foz, 110', 'Presidente Prudente', 'SP', '1899111-6366', NULL);


--
-- TOC entry 3367 (class 0 OID 16413)
-- Dependencies: 220
-- Data for Name: item; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3368 (class 0 OID 16420)
-- Dependencies: 221
-- Data for Name: produto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.produto VALUES (2, 1, 1, 'Chopp', 5.80, 'chopp artesanal');
INSERT INTO public.produto VALUES (1, 1, 1, 'Vinho', 9.99, 'Importado');
INSERT INTO public.produto VALUES (3, 1, 1, 'vodka', 8.00, 'importada');
INSERT INTO public.produto VALUES (5, 1, 1, 'suco de uva', 4.50, 'natural');
INSERT INTO public.produto VALUES (6, 1, 3, 'cerveja heineken', 12.00, 'super gelada');
INSERT INTO public.produto VALUES (4, 1, 1, 'suco de laranja', 5.60, 'natural');
INSERT INTO public.produto VALUES (7, 2, 4, 'Batata frita', 18.00, 'porção grande, acompanha molho');


--
-- TOC entry 3370 (class 0 OID 16428)
-- Dependencies: 223
-- Data for Name: unidade; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.unidade VALUES (1, 'copo');
INSERT INTO public.unidade VALUES (2, 'garrafa 600ml');
INSERT INTO public.unidade VALUES (3, 'garrafa long ne');
INSERT INTO public.unidade VALUES (5, 'meia porção');
INSERT INTO public.unidade VALUES (4, 'porção');
INSERT INTO public.unidade VALUES (6, 'kit');


--
-- TOC entry 3382 (class 0 OID 0)
-- Dependencies: 215
-- Name: categoria_cat_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoria_cat_id_seq', 4, true);


--
-- TOC entry 3383 (class 0 OID 0)
-- Dependencies: 217
-- Name: comanda_com_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comanda_com_id_seq', 1, true);


--
-- TOC entry 3384 (class 0 OID 0)
-- Dependencies: 219
-- Name: garcon_gar_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.garcon_gar_id_seq', 2, true);


--
-- TOC entry 3385 (class 0 OID 0)
-- Dependencies: 222
-- Name: produto_prod_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.produto_prod_id_seq', 7, true);


--
-- TOC entry 3386 (class 0 OID 0)
-- Dependencies: 224
-- Name: unidade_uni_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.unidade_uni_id_seq', 6, true);


--
-- TOC entry 3209 (class 2606 OID 16440)
-- Name: item item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_pkey PRIMARY KEY (com_id, prod_id);


--
-- TOC entry 3203 (class 2606 OID 16442)
-- Name: categoria pk_categoria; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT pk_categoria PRIMARY KEY (cat_id);


--
-- TOC entry 3205 (class 2606 OID 16444)
-- Name: comanda pk_comanda; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comanda
    ADD CONSTRAINT pk_comanda PRIMARY KEY (com_id);


--
-- TOC entry 3207 (class 2606 OID 16446)
-- Name: garcon pk_garcon; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.garcon
    ADD CONSTRAINT pk_garcon PRIMARY KEY (gar_id);


--
-- TOC entry 3211 (class 2606 OID 16450)
-- Name: produto pk_produto; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT pk_produto PRIMARY KEY (prod_id);


--
-- TOC entry 3213 (class 2606 OID 16454)
-- Name: unidade pk_unidade; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidade
    ADD CONSTRAINT pk_unidade PRIMARY KEY (uni_id);


--
-- TOC entry 3214 (class 2606 OID 16455)
-- Name: comanda fk_comanda; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comanda
    ADD CONSTRAINT fk_comanda FOREIGN KEY (gar_id) REFERENCES public.garcon(gar_id);


--
-- TOC entry 3215 (class 2606 OID 16460)
-- Name: item fk_itemcomd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT fk_itemcomd FOREIGN KEY (com_id) REFERENCES public.comanda(com_id);


--
-- TOC entry 3216 (class 2606 OID 16465)
-- Name: item fk_itemprod; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT fk_itemprod FOREIGN KEY (prod_id) REFERENCES public.produto(prod_id);


--
-- TOC entry 3217 (class 2606 OID 16480)
-- Name: produto fk_prodcat; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT fk_prodcat FOREIGN KEY (cat_id) REFERENCES public.categoria(cat_id);


--
-- TOC entry 3218 (class 2606 OID 16485)
-- Name: produto fk_produni; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT fk_produni FOREIGN KEY (uni_id) REFERENCES public.unidade(uni_id);


-- Completed on 2024-05-09 19:58:09

--
-- PostgreSQL database dump complete
--

