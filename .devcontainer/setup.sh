# .devcontainer/setup.sh
#!/bin/bash

echo "Running post-creation setup script..."

echo "Waiting for SQL Server to start..."
# Use 'db' as the hostname since both are in the same docker-compose network
/opt/mssql-tools/bin/sqlcmd -S db,1433 -U sa -P 'YourStrongPassword123!' -Q "SELECT 1" &>/dev/null
until [ $? -eq 0 ]; do
    echo "SQL Server is unavailable - sleeping"
    sleep 5
    /opt/mssql-tools/bin/sqlcmd -S db,1433 -U sa -P 'YourStrongPassword123!' -Q "SELECT 1" &>/dev/null
done
echo "SQL Server is up!"

echo "Creating sample database and table..."
/opt/mssql-tools/bin/sqlcmd -S db,1433 -U sa -P 'YourStrongPassword123!' -Q "
    IF NOT EXISTS (SELECT name FROM master.sys.databases WHERE name = N'MyCodespaceDB')
    CREATE DATABASE MyCodespaceDB;
"

/opt/mssql-tools/bin/sqlcmd -S db,1433 -U sa -P 'YourStrongPassword123!' -d MyCodespaceDB -Q "
    IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = N'Customers')
    CREATE TABLE Customers (
        Id INT PRIMARY KEY IDENTITY(1,1),
        FirstName NVARCHAR(50),
        LastName NVARCHAR(50),
        Email NVARCHAR(100)
    );
"

# Insert some sample data (only if the table is empty)
/opt/mssql-tools/bin/sqlcmd -S db,1433 -U sa -P 'YourStrongPassword123!' -d MyCodespaceDB -Q "
    IF NOT EXISTS (SELECT * FROM Customers)
    INSERT INTO Customers (FirstName, LastName, Email) VALUES
    ('Alice', 'Smith', 'alice@example.com'),
    ('Bob', 'Johnson', 'bob@example.com');
"

echo "Database setup complete."