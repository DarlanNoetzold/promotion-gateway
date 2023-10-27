-- Inserção de dados na tabela public.promotionmodel (Inserção 1)
INSERT INTO public.promotionmodel (promoid, applytocode, discountlimit, enddate, exceptionparentid, promocompdesc, promocomptype, promodescription, promodtlid, promoname, promotiontype, rules, startdate, value, valuetype)
VALUES ('8b71d0c5-9f98-4c4a-815c-55e4c0518f6a', 'CODE123', 50.0, '2023-12-31 23:59:59', NULL, 'Description of Promotion', 1.0, 'Promotion Description', 1.0, 'PromoName 1', 1, 'Rule 1', '2023-01-01 00:00:00', 10.0, 0);

-- Inserção de dados na tabela public.promotionmodel (Inserção 2)
INSERT INTO public.promotionmodel (promoid, applytocode, discountlimit, enddate, exceptionparentid, promocompdesc, promocomptype, promodescription, promodtlid, promoname, promotiontype, rules, startdate, value, valuetype)
VALUES ('d3e6dbb6-4f9c-4b4d-843e-8f0d8bdc5e58', 'CODE456', 25.0, '2023-11-30 23:59:59', NULL, 'Another Description', 2.0, 'Another Promo', 2.0, 'PromoName 2', 0, 'Rule 2', '2023-02-01 00:00:00', 20.0, 1);

-- Inserção de dados na tabela public.couponmodel (Inserção 1)
INSERT INTO public.couponmodel (couponid, code, promoid)
VALUES ('26c0dd7d-2c63-4a5e-9b87-94d7b5cb9c98', 'COUPON123', '8b71d0c5-9f98-4c4a-815c-55e4c0518f6a');

-- Inserção de dados na tabela public.couponmodel (Inserção 2)
INSERT INTO public.couponmodel (couponid, code, promoid)
VALUES ('b3ac9a42-708e-47bb-8f41-299d438cb9f7', 'COUPON456', 'd3e6dbb6-4f9c-4b4d-843e-8f0d8bdc5e58');