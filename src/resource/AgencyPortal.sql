CREATE TABLE AGENTCONFIGINFO(		
	AGENTCID	int IDENTITY(19,1),
	CARRIERID	varchar(10)	,
	AGENTID	varchar(10)	,
	AGENCYNAME	varchar(50)	,
	NAME	varchar(50)	,
	EMAIL	varchar(50),	
	PHONE	varchar(20)	,
	CITY	varchar(30)	,
	ADDRESS1	varchar(50)	,
	ADDRESS2	varchar(50)	,
	STATE	varchar(10)	,
	ZIP	varchar(10)	,
	LASTDOWNLOADDATE	numeric(19,0),
	LASTPAYMENTDT	numeric(19,0),	
	LASTPAYMENTAMT	numeric(19,0)	
	);
CREATE TABLE AGENTPAYMENTCONFIG(		
	id	int IDENTITY(19,1),
	AGENTCID	numeric(19,0)	,
	type	varchar(10)	,
	LASTFOURDIGIT	varchar(4)	,
	EXPIRYDATE	varchar(6)	,
	SECURITYCODE	varchar(200),	
	CARDTYPE	varchar(10)	,
	BANKNAME	varchar(30)	,
	ACCOUNTNUMBER	varchar(16)	,
	ROUTINGNUMBER	varchar(30)	,
	ACCOUNTTYPE	varchar(30)	,
	ACTIVE	varchar(1)	,
	CREATIONDT	datetime  NOT NULL DEFAULT GETDATE(),
	NAME	varchar(30),	
	AGREE	int	,
	ACCOUNTID	varchar(200),
	CUSTOMERID	varchar(200)
	);
CREATE TABLE MONTHLYBILL(		
	ID	int IDENTITY(19,1),	
	AGENTCID	numeric(19,0)	NOT NULL,
	MONTH	int	NOT NULL,
	YEAR	int	NOT NULL,
	TYPE	varchar(1),	
	AMOUNT	decimal(10,2),	
	DUEDATE	decimal(19, 0),
	CREATIONDT	decimal(19, 0),	
	TRANSACTIONDT	decimal(19, 0)
	PRIMARY KEY (AGENTCID,MONTH,YEAR,TYPE)
	);	
	CREATE TABLE DEPOSIT(	
	ID	int IDENTITY(19,1),
	AGENTCID	numeric(19,0)	,
	MONTH	int	,
	YEAR	int	,
	TYPE	varchar(1),	
	AMOUNT	decimal(10,2),	
	CREATIONDT	decimal(19, 0),
	STATUS varchar(10)
	PRIMARY KEY (AGENTCID,MONTH,YEAR)
	);	
	CREATE TABLE JOBSDETAIL(		
	ID	int IDENTITY(15,1),	
	JOBID	varchar(30)	,	
	JOBNAME	varchar(30),		
	JOBDESC	varchar(50)	,	
	JOBGRPNAME	varchar(30)	,	
	JOBTRIGNAME	varchar(30),		
	JOBTRIGSTR	varchar(30)	,	
	JOBCLASSNAME	varchar(30),		
	ACTIVE	varchar(1),		
	LASTRUNDATE	date,		
	LASTRUNSTATUS	varchar(30)		
	);	
	CREATE TABLE JOBSCONFIG(		
	JOBID	varchar(10),	
	PROPNAME	varchar(20),	
	PROPVALUE	varchar(100)
	);	
	CREATE TABLE JOBACTIVITY(		
	ID	int IDENTITY(20,1),
	JOBID	varchar(10),	
	ACTIVITYSTARTTIME	decimal(19,0),	
	ACTIVITYENDTIME	decimal(19,0),	
	STATUS	varchar( 1),
	nooftransactionfailed numeric(19,0),
	nooftransactionsuccess numeric(19,0)
	);
	CREATE TABLE JOBACTIVITYTRANSDETAIL(		
	ID	int IDENTITY(25,1),
	JACTID	numeric(25,0),
	AGENTCID	numeric(25,0),	
	AMOUNT	decimal(10,2),	
	LASTFOURDIGIT	varchar(4),	
	ROUTINGNUMBER	varchar(30)	,
	CREATIONDT	numeric(19,0),	
	TXNDT	numeric(19,0)	,
	DESCRIPTION	varchar(400),	
	STATUS	varchar( 20),
	TRANSACTIONID	varchar(4),	
	CHARGEID	varchar(30)	
	);
	
	CREATE TABLE JOBSDETAIL(		
	ID	int IDENTITY(15,1),	
	JOBID	varchar(10)	,	
	JOBNAME	varchar(30),		
	JOBDESC	varchar(50)	,	
	JOBGRPNAME	varchar(10)	,	
	JOBTRIGNAME	varchar(10),		
	JOBTRIGSTR	varchar(20)	,	
	JOBCLASSNAME	varchar(30),		
	ACTIVE	varchar(1),		
	LASTRUNDATE	date,		
	LASTRUNSTATUS	varchar(10)		
	);		
	CREATE TABLE CONFIGDOWNLOAD(		
	ID	int IDENTITY(1,1),
	AGENTID	varchar(50)	,
	lastdownload	decimal(19,0)
	);