BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "korisnik" (
	"id"	INTEGER,
	"naziv"	TEXT,
	"broj_telefona"	TEXT,
	"adresa"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "kurir" (
	"id"	INTEGER,
	"naziv"	TEXT,
	"broj_telefona"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "posiljka" (
	"id"	INTEGER,
	"opis"	TEXT,
	"adresa" TEXT,
	"posiljaoc"	INTEGER,
	"primalac"	INTEGER,
	"kurir"	INTEGER,
    "tezina"	INTEGER,
    "cijena_dostave" INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("kurir") REFERENCES "kurir"("id"),
	FOREIGN KEY("posiljaoc") REFERENCES "korisnik"("id"),
	FOREIGN KEY("primalac") REFERENCES "korisnik"("id")
);
INSERT INTO "korisnik" VALUES (1,'Tarik Beganović','123','neka');
INSERT INTO "korisnik" VALUES (2,'Meho Mehić','456','ona');
INSERT INTO "korisnik" VALUES (3,'Ja','123456','ova');
INSERT INTO "kurir" VALUES (1,'Kurir 1','777');
INSERT INTO "kurir" VALUES (2,'Kurir 2','888');
INSERT INTO "posiljka" VALUES (1,'Televizor','ona',1,2,1,2,10);
INSERT INTO "posiljka" VALUES (2,'Sportska oprema','ova',2,3,2,8,10);
COMMIT;
