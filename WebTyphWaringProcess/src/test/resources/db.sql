use TyphoonWarning;

drop table if exists WebTyphWarning ;
create table WebTyphWarning(
  `TyphName`  char(20),
  `AlarmDate` datetime,
  `State`   int,
  `ModifyDate`   datetime
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table if exists WebTyphName2No;
create table WebTyphName2No(
  `TyphNo`  char(7),
  `TyphNameEng` char(20),
  `TyphNameCht`   char(30)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



