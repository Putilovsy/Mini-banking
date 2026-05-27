GUI Implementation: Java Swing library.

DB Connection in JAVA: The connection to the local SQLite database was implemented using a dedicated driver downloaded via Maven
The database connection logic is encapsulated within the DatabaseRequest.java class.

Reading from DB in JAVA: Data reading is executed via SELECT SQL queries.
Specific methods for user authorization, retrieving account balances, and fetching transaction history were written in DatabaseRequest.java.
The extracted data is then passed to and displayed in the ClientWindow and AdminWindow GUI classes.

Writing to DB in JAVA: Data writing is handled in DatabaseRequest.java using data modification queries (UPDATE, INSERT).
The core feature of fund transfers (transferMoney) is implemented securely using SQL transactions via setAutoCommit(false), commit(), and rollback() methods.
