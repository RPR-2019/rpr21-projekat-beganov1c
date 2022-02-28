BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "user" (
	"id"	INTEGER,
	"name"	TEXT,
	"telephone_number"	TEXT,
	"address"	TEXT,
	"city" TEXT,
	"zip_code" INTEGER,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "courier" (
	"id"	INTEGER,
	"name"	TEXT,
	"telephone_number"	TEXT,
	"username" TEXT,
	"password" TEXT,
	"image" TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "package" (
	"id"	INTEGER,
	"description"	TEXT,
	"address" TEXT,
	"sender"	INTEGER,
	"receiver"	INTEGER,
	"courier"	INTEGER,
    "weight"	INTEGER,
    "delivery_price" INTEGER,
    "city" TEXT,
    "zip_code" INTEGER,
    "sending_time"	TEXT,
    "delivery_time"	TEXT,
    "order_status" TEXT,
	PRIMARY KEY("id"),
	FOREIGN KEY("courier") REFERENCES "courier"("id"),
	FOREIGN KEY("sender") REFERENCES "user"("id"),
	FOREIGN KEY("receiver") REFERENCES "user"("id")
);
CREATE TABLE IF NOT EXISTS "register" (
    "courier"	INTEGER,
    "package"	INTEGER,
    FOREIGN KEY("courier") REFERENCES "courier"("id"),
    FOREIGN KEY("package") REFERENCES "package"("id")
);
CREATE TABLE IF NOT EXISTS "manager" (
    "id"	INTEGER,
    "name"	TEXT,
    "username" TEXT,
    "password" TEXT,
    PRIMARY KEY("id")
);
INSERT INTO "user" VALUES (1,'/','/','/', '/',0);
INSERT INTO "user" VALUES (2,'Tarik Beganović','123','neka', 'Cazin', 77220);
INSERT INTO "user" VALUES (3,'Meho Mehić','456','ona', 'Cazin', 77220);
INSERT INTO "user" VALUES (4,'Ja','123456','ova', 'Cazin', 77220);
INSERT INTO "courier" VALUES (1,'Courier 1','777','ccourier1','courier1',NULL);
INSERT INTO "courier" VALUES (2,'Courier 2','888','ccourier2','courier2',NULL);
INSERT INTO "package" VALUES (1,'Televizor','ona',2,3,1,2,10,'Cazin',77220,'14-01-2017 11:42:32',NULL,'In transport');
INSERT INTO "package" VALUES (2,'Sportska oprema','ova',3,4,2,8,10,'Cazin',77220,'14-03-2020 13:42:00',NULL,'In warehouse');
INSERT INTO "manager" VALUES (1,'Manager 1','manager1','manager1');
COMMIT;


