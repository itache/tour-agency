/*
File name: C:\Users\itache\Desktop\final.sql
Creation date: 09/21/2016
Created by MySQL to PostgreSQL 2.5 [Demo]
--------------------------------------------------
More conversion tools at http://www.convert-in.com
*/

/*
Table structure for table 'public.order'
*/

DROP TABLE IF EXISTS "public"."order" CASCADE;
CREATE TABLE "public"."order" (
	"id" SERIAL NOT NULL,
	"customer_id" BIGINT NOT NULL,
	"tour_id" BIGINT NOT NULL,
	"discount" INTEGER NOT NULL,
	"price" INTEGER NOT NULL,
	"status" VARCHAR(24)  NOT NULL,
	"date" TIMESTAMP
) WITH OIDS;
DROP INDEX IF EXISTS "primary";
ALTER TABLE "public"."order" ADD CONSTRAINT "primary" PRIMARY KEY("id");
DROP INDEX IF EXISTS "customer_id";
CREATE INDEX "customer_id" ON "public"."order"("customer_id");
DROP INDEX IF EXISTS "tour_id";
CREATE INDEX "tour_id" ON "public"."order"("tour_id");

/*
Dumping data for table 'public.order'
*/

COPY public.order(id,customer_id,tour_id,discount,price,status,date) FROM stdin;
27	23	5	0	1000	PAID	2016-08-25 21:05:32
28	23	2	2	1038	RESERVED	2016-08-25 21:05:47
29	23	1	7	372	RESERVED	2016-08-26 19:44:20
30	23	4	1	594	RESERVED	2016-08-29 09:00:33
31	23	3	0	650	CANCELED	2016-08-29 21:51:05
32	23	5	8	920	RESERVED	2016-08-31 20:32:04
33	23	11	17	1273	RESERVED	2016-08-31 21:10:42
34	23	12	54	459	RESERVED	2016-08-31 21:41:09
35	23	9	6	4136	RESERVED	2016-08-31 21:44:36
36	26	12	24	760	PAID	2016-09-02 11:03:32
37	26	1	1	396	CANCELED	2016-09-02 11:03:35
38	23	10	2	686	PAID	2016-09-02 11:53:52
\.

/*
Table structure for table 'public.token'
*/

DROP TABLE IF EXISTS "public"."token" CASCADE;
CREATE TABLE "public"."token" (
	"id" SERIAL NOT NULL,
	"user_id" BIGINT NOT NULL,
	"token" VARCHAR(36)  NOT NULL,
	"expiry_date" TIMESTAMP
) WITH OIDS;
DROP INDEX IF EXISTS "primary";
ALTER TABLE "public"."token" ADD CONSTRAINT "primary" PRIMARY KEY("id");

/*
Dumping data for table 'public.token'
*/

COPY public.token(id,user_id,token,expiry_date) FROM stdin;
3	17	2edb2f41-5606-4402-9c33-9fb9207abd40	2016-08-27 09:56:43
7	23	898993e5-60f9-4c8f-ac78-59797a1e9242	2016-09-03 07:26:28
8	24	29173835-abf0-477d-95eb-ed41ae1cb593	2016-09-03 07:36:56
10	25	85a750b0-536a-42a9-b841-4aeddbf24c96	2016-09-03 07:40:17
11	26	d48dfe89-b7bd-4bf9-9b17-eb2484b05c35	2016-09-03 08:04:06
\.

/*
Table structure for table 'public.tour'
*/

DROP TABLE IF EXISTS "public"."tour" CASCADE;
CREATE TABLE "public"."tour" (
	"id" SERIAL NOT NULL,
	"price" INTEGER NOT NULL,
	"is_hot" SMALLINT NOT NULL,
	"type" VARCHAR(27)  NOT NULL,
	"hotel_level" VARCHAR(33)  NOT NULL,
	"persons" INTEGER NOT NULL,
	"start" DATE,
	"duration" SMALLINT NOT NULL,
	"country_code" VARCHAR(3) ,
	"discount_step" INTEGER NOT NULL,
	"max_discount" INTEGER NOT NULL
) WITH OIDS;
DROP INDEX IF EXISTS "primary";
ALTER TABLE "public"."tour" ADD CONSTRAINT "primary" PRIMARY KEY("id");

/*
Dumping data for table 'public.tour'
*/

COPY public.tour(id,price,is_hot,type,hotel_level,persons,start,duration,country_code,discount_step,max_discount) FROM stdin;
1	401	0	VACATION	FIVE_STARS	2	2016-09-09	12	IS	1	11
2	1060	1	EXCURSION	FOUR_STARS	4	2016-09-16	14	IS	2	10
3	650	0	EXCURSION	FOUR_STARS	1	2016-08-16	3	GL	0	0
4	600	0	VACATION	THREE_STARS	8	2016-09-03	5	UA	1	3
5	1000	0	EXCURSION	FOUR_STARS	4	2016-09-23	4	US	2	10
9	4400	0	EXCURSION	FOUR_STARS	6	2016-09-30	9	NZ	2	16
10	700	0	VACATION	FIVE_STARS	4	2016-09-29	6	TZ	1	3
11	1534	0	VACATION	FIVE_STARS	3	2016-09-29	8	HR	1	30
12	1000	1	SHOPPING	FIVE_STARS	10	2016-09-15	5	IT	4	70
\.

/*
Table structure for table 'public.tour_description_en'
*/

DROP TABLE IF EXISTS "public"."tour_description_en" CASCADE;
CREATE TABLE "public"."tour_description_en" (
	"id" SERIAL NOT NULL,
	"tour_id" BIGINT NOT NULL,
	"title" VARCHAR(60)  NOT NULL,
	"description" TEXT NOT NULL
) WITH OIDS;
DROP INDEX IF EXISTS "primary";
ALTER TABLE "public"."tour_description_en" ADD CONSTRAINT "primary" PRIMARY KEY("id");
DROP INDEX IF EXISTS "tour_id";
CREATE UNIQUE INDEX "tour_id" ON "public"."tour_description_en"("tour_id");

/*
Dumping data for table 'public.tour_description_en'
*/

COPY public.tour_description_en(id,tour_id,title,description) FROM stdin;
1	1	Around Iceland	Iceland - a country that is able to fall in love at first sight and absolutely always! And go to the first meeting with the northern island in the summer twice as nice - it was at this time Iceland is revealed in full glory - the white nights, stormy waterfalls, verdant valleys and the "Martian" landscape inaccessible places ... We need to go safely to land of fire and ice and fill online photo albums unrealistic images!\nThis classic version travel around the whole of Iceland with a guide introduces you to the most popular places of the island - the famous "Golden Ring", black-sanded South Beach, Glacial Lagoon, sparsely populated region of the eastern fjords and Lake Myvatn area known ...\nVery emotional journey through the enigmatic North Island.
2	2	In search of the treasure of Iceland	Not forgettable tour where you feel like the only person in the world next to the pristine natural landscape. You will see the most interesting places of the southern and western Iceland.
3	3	Greenland: bird''s-eye view	We offer you to experience the majesty and power of nature Greenland bird''s-eye view. Beautiful and fertile land in South Greenland, with its rich history of the Vikings, Nuuk - the capital of Greenland, Ilulissat fjord with the same name, on which the city is located. All this will leave a lasting impression in your memory.
4	4	Cycling tour " Through the mountains to the sea "	You can enjoy the unforgettable scenery of the mountain area , to meet the sunrises and sunsets to accompany , pedaling .. will produce an indelible impression on the reliefs you, the amount of wild berries and fruits , descents and ascents , and as a reward - descent to the sea , where you can easily relax under the scorching sun of the same Crimea .
5	5	Grand Canyon	Grand Canyon - one of the deepest canyons in the world . Located on the Colorado Plateau , Arizona, USA , in the national park " Grand Canyon " and Indian tribal reservations Navajo , Havasupai and hualapay . Canyon of the Colorado River is cut in the thickness of limestone , shale and sandstone . Canyon length - 446 km. The width (at the plateau) is between 6 and 29 km, at the bottom - less than one kilometer . Depth - up to 1800 m .
9	9	New Zealand	Introduction to the main city of New Zealand, Auckland - the "city of sails " , the unique nature reserves and natural parks , volcanic wonders of the world , the valleys of geysers and hot springs , Maori culture , lakes and vineyards . Your unforgettable journey will end in the capital city, Wellington . Accommodation in unique lodges located in the picturesque places on the shores of lakes and national parks will make you forget about the hustle and bustle and enjoy the peace and quiet of the country life " long white cloud " .
10	10	Tale in Zanzibar	Zanzibar - a magical land, which is filled with exotic charm. Especially famous for its magnificent island plyazhami.Eto km long promenade with palm trees, white sand, turquoise water and tropical trees. Fans of diving and snorkeling will be captivated by the underwater richness and color mira.Priroda generous here the sun, gives great views and surprising originality of flora and fauna. That ocean, which does not tire of surprise underwater world and the longest white beach of fine sand, in which nice to walk and ride a bike, and the jungle with exotic baobabs, flexible vines and flocks of monkeys, and the night sky with millions of stars, to whom, it seems you can get rukoy.Zanzibar - one of the most romantic islands and beautiful corners of our planet, the place of expensive high-end vacation.
11	11	Bewitching Dalmatia	Split - the center of Dalmatia , the second largest city in Croatia , where the hot Dalmatian blood spiced spicy aroma of fennel tart glass of wine and the melodious song . Beautiful bay , azure sea , a handful of pearl islands , the continental hinterland with its ethno-tourism is what sets this part of the Croatian coast from a number of other resorts .
12	12	 Rome and its wealth	Walking along the main Italian cities filled with attractions like a barrel - herring , are pretty tiring . And the best rest , as you know - a change of activity. What else is famous for Italy, in addition to the monuments of architecture and art world values ​​? That''s right, shopping ! So, before you stuff about shopping in Rome: a description of the main shopping areas , shopping centers , a list of the most important shops of the city and other useful information for the shopaholic .
\.

/*
Table structure for table 'public.tour_description_ru'
*/

DROP TABLE IF EXISTS "public"."tour_description_ru" CASCADE;
CREATE TABLE "public"."tour_description_ru" (
	"id" SERIAL NOT NULL,
	"tour_id" BIGINT NOT NULL,
	"title" VARCHAR(60)  NOT NULL,
	"description" TEXT NOT NULL
) WITH OIDS;
DROP INDEX IF EXISTS "primary";
ALTER TABLE "public"."tour_description_ru" ADD CONSTRAINT "primary" PRIMARY KEY("id");
DROP INDEX IF EXISTS "tour_id";
CREATE UNIQUE INDEX "tour_id" ON "public"."tour_description_ru"("tour_id");

/*
Dumping data for table 'public.tour_description_ru'
*/

COPY public.tour_description_ru(id,tour_id,title,description) FROM stdin;
1	1	Вокруг Исландии	Исландия – страна, которая способна влюбить в себя с первого взгляда и абсолютно всегда! А отправиться на первую встречу с этим северным островом летом вдвойне приятно – ведь именно в это время Исландия раскрывается в полной красе – белые ночи, бурные водопады, зеленеющие долины и «марсианские» пейзажи труднодоступных мест… Нужно смело отправляться на землю огня и льда и заполнять онлайн фотоальбомы нереальными снимками!    \nЭтот классический вариант поездки вокруг всей Исландии с русскоговорящим гидом знакомит Вас с самыми популярными местами острова – знаменитое «Золотое кольцо», чернопесчаный Южный берег, Ледниковая Лагуна, малонаселённая область восточных фьордов и район известного озера Миватн…\nОчень душевное путешествие по загадочному северному острову.
2	2	В поисках сокровищ Исландии	Hезабываемый тур, где вы почувствуете себя единственным человеком на Земле рядом с первозданным природным ландшафтом. Вы увидите самые интересные места южной и западной Исландии.
3	3	Гренландия: С высоты птичьего полёта	Предлагаем Вам ощутить величие и силу природы острова Гренландия с высоты птичьего полёта. Прекрасные и плодородные земли Южной Гренландии с его богатой историей Викингов, Нуук – столица Гренландии, Илулиссат с одноимённым фьордом, на котором этот город расположен. Всё это оставит у Вас неизгладимые впечатления в Вашей памяти.
4	4	Велопрогулка "Через горы к морю"	Вы можете наслаждаться незабываемыми  пейзажами горного Крыма, встречать рассветы, и провожать закаты , крутя педали.. Неизгладимое впечатление произведет на вас рельефы, количество диких ягод и фруктов, спуски и подъемы, и как награда - спуск к морю, где вы легко сможете расслабиться под палящем солнцем все того же Крыма.
5	5	Большой каньон	Большой каньон - один из глубочайших каньонов в мире. Находится на плато Колорадо, штат Аризона, США, на территории национального парка «Гранд-Каньон», а также резерваций индейцев племен навахо, хавасупай и хуалапай. Каньон прорезан рекой Колорадо в толще известняков, сланцев и песчаников. Длина каньона — 446 км. Ширина (на уровне плато) колеблется от 6 до 29 км, на уровне дна — менее километра. Глубина — до 1800 м.
9	9	Новая Зеландия	Знакомство с главным городом Новой Зеландии, Оклендом - "городом парусов", уникальными заповедниками и природными парками, чудесами вулканического мира, долинами гейзеров и термальными источниками, культурой маори, озерами и винодельнями. Ваше незабываемое путешествие закончится в столице страны Веллингтоне. Проживание в уникальных лоджах, расположенных в живописных местах на берегах озер и в национальных парках, позволит Вам позабыть о суете и окунуться в мирный и спокойный быт страны "длинного белого облака".
10	10	Сказка в Занзибаре	Занзибар - магическая земля, которая наполнена экзотическими очарованиями. Особенно славится остров своими великолепными пляжами.Это километровые набережные с пальмами, белым песком, бирюзовой водой и тропическими деревьями. Любители дайвинга и подводного плавания будут очарованы насыщенностью и цветом подводного мира.Природа здесь щедра на солнце, дарит прекрасные виды и удивляет неповторимостью флоры и фауны. Это и океан, который не устает удивлять подводным миром, и протяженный белоснежный пляж из мелкого песка, по которому приятно гулять и кататься на велосипедах, и джунгли с экзотическими баобабами, гибкими лианами и стаями обезьян, и ночное небо с миллионами звезд, до которых, кажется, можно достать рукой.Занзибар – один из самых романтических островов и красивейших уголков нашей планеты, место дорогого элитного отдыха.
11	11	Чарующая Далмация	Сплит - центр Далмации,второй по величине город в Хорватии, где горячая далматинская кровь сдобрена пряным ароматом фенхеля, бокалом терпкого вина и мелодичной песней. Красивые бухты, лазурное море, горсть жемчужных островов, континентальная глубинка с ее этнотуризмом выгодно выделяет эту часть хорватского побережья из ряда других курортов.
12	12	Рим и его богатства	Прогулки по главному городу Италии, наполненному достопримечательностями как бочка — сельдью, могут изрядно утомить. А лучший отдых, как известно — смена деятельности. А чем еще славится Италия, кроме памятников архитектуры и искусства мирового значения? Правильно, шоппингом! Итак, перед вами материал про шоппинг в Риме: описание основных шоппинг-районов, торговых центров, список самых значимых магазинов города и другая полезная для шопоголика информация.
\.

/*
Table structure for table 'public.user'
*/

DROP TABLE IF EXISTS "public"."user" CASCADE;
CREATE TABLE "public"."user" (
	"id" SERIAL NOT NULL,
	"login" VARCHAR(50)  NOT NULL,
	"password" VARCHAR(32)  NOT NULL,
	"email" VARCHAR(50)  NOT NULL,
	"role" VARCHAR(24)  NOT NULL,
	"blocked" SMALLINT NOT NULL,
	"enabled" SMALLINT NOT NULL
) WITH OIDS;
DROP INDEX IF EXISTS "primary";
ALTER TABLE "public"."user" ADD CONSTRAINT "primary" PRIMARY KEY("id");
DROP INDEX IF EXISTS "login";
CREATE UNIQUE INDEX "login" ON "public"."user"("login");

/*
Dumping data for table 'public.user'
*/

COPY public.user(id,login,password,email,role,blocked,enabled) FROM stdin;
1	admin	21232f297a57a5a743894a0e4a801fc3		ADMIN	0	1
3	manager	1d0258c2440a8d19e716292b231e3190		MANAGER	0	1
16	LISA	54a26fd655bb9509d1203860320a8ce0	li8a@mail.ru	CUSTOMER	0	1
17	Vitaliy	fb9f550260872bc52082e0365530177a	sergey.karpachev@gmail.com	CUSTOMER	0	0
23	new	f5a331c1566784f68e16555b1ba8f2a5	vitaliy.karpachev@gmail.com	CUSTOMER	0	1
26	customer	f5a331c1566784f68e16555b1ba8f2a5	customer@gmail.co	CUSTOMER	0	1
\.
