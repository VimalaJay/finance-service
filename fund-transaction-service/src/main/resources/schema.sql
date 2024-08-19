create table FUND_TRANSACTION(
  ID int not null AUTO_INCREMENT,
  TRANSACTION_ID BigInt not null UNIQUE,
  TRANSACTION_TYPE varchar(20) not null,
  TRANSACTION_STATUS varchar(10),
  ACCOUNT_ID varchar(100) not null,
  AMOUNT BigInt not null,
  PRIMARY KEY ( TRANSACTION_ID )
);

